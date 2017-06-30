package com.lolchat.myanmarking.myanchat.network.xmpp

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.model.xmpp.ChampionId
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomInteractor
import com.lolchat.myanmarking.myanchat.io.storage.room.RoomPersistentDb
import com.lolchat.myanmarking.myanchat.io.storage.room.model.ChampionBasic
import com.lolchat.myanmarking.myanchat.other.parser.RiotXmlParser
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntries
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.roster.RosterListener
import org.jxmpp.jid.BareJid
import org.jxmpp.jid.Jid
import timber.log.Timber

class RiotRosterManager(
        val dbInteractor: RoomInteractor
) : RosterListener, RosterEntries {

    private val INFO_ENABLED = true
    private val friendList: MutableMap<String, FriendEntity> = hashMapOf()
    private var initialized: Boolean = false
    private lateinit var roster: Roster

    private val champListMap: HashMap<Int, ChampionId> = hashMapOf()
    private val changeListener: MutableList<IFriendChangeListener> = mutableListOf()

    fun init(conn: AbstractXMPPConnection) {
        if (!(conn.isConnected && conn.isAuthenticated))
            throw IllegalStateException()

        initChampListName()
                .subscribe(
                        {
                            champListMap.putAll(
                                    it.associateBy(
                                            { it.id },
                                            { ChampionId(it.name, it.key) }
                                    ))

                            this.roster = Roster.getInstanceFor(conn)
                            roster.getEntriesAndAddListener(this, this)
                        },
                        { Timber.e(it)}
                )
    }

    fun initChampListName(): Observable<List<ChampionBasic>> {
        return dbInteractor.getAllChamp()
                .onErrorReturn {
                    Timber.e(it)
                    emptyList()
                }
                .subscribeOn(Schedulers.computation())
    }

    // initial fetch
    override fun rosterEntries(rosterEntries: MutableCollection<RosterEntry>) {
        // we clear the map just in case
        friendList.clear()

        rosterEntries.forEach { entry ->
            val presence = getPresence(entry.jid.asBareJid())
            val name = entry.name

            if (presence.from != null) {
                friendList.put(
                        name,
                        getFriendEntityFromPresence(presence, name)
                )
            }
            Timber.i(":oo ${presence.toString()}")
        }
        notifyListenersDataChange(true)
        this.initialized = true
    }

    private fun getPresence(jid: BareJid): Presence = roster.getPresence(jid)
    private fun getEntry(jid: BareJid): RosterEntry = roster.getEntry(jid)
    private fun getChampionNameForId(champId: Int): ChampionId {
        val champName = champListMap[champId]
        return champName ?: ChampionId(EMPTY_STRING, EMPTY_STRING)
    }
    private fun getFriendEntityFromPresence(presence: Presence, name: String): FriendEntity{
        val rootElement = RiotXmlParser.buildCustomAttrFromStatusMessage(presence)
        val champId: Int = RiotXmlParser.getChampionId(rootElement)
        val gameStatus: GameStatus = RiotXmlParser.getGameStatus(rootElement)
        val champName: ChampionId = getChampionNameForId(champId)
        val isOnline = presence.isAvailable

        return FriendEntity(
                presence.from.asBareJid().toString(),
                name,
                RiotXmlParser.getProfileIcon(rootElement),
                RiotXmlParser.getStatusMessage(rootElement),
                isOnline,
                gameStatus,
                RiotXmlParser.getPresenceMode(rootElement, presence),
                RiotXmlParser.getRankedLeagueTierAnddivision(rootElement, presence),
                RiotXmlParser.getPlayingData(rootElement, champName, gameStatus, isOnline),
                RiotXmlParser.getSkinInfo(rootElement)
        )
    }

    override fun entriesDeleted(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            val entry = getEntry(jid.asBareJid())
            friendList.remove(entry.name)
        }
        notifyListenersDataChange(false)
    }
    override fun entriesAdded(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            val entry = getEntry(jid.asBareJid())
            val newPresence = getPresence(jid.asBareJid())
            val name = entry.name
            friendList.put(name, getFriendEntityFromPresence(newPresence, name))
        }
        notifyListenersDataChange(false)
    }

    override fun entriesUpdated(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            val entry = getEntry(jid as BareJid)
            val presence = getPresence(jid)
            val name = entry.name
            friendList.put(name, getFriendEntityFromPresence(presence, entry.name))
        }
        notifyListenersDataChange(false)
    }
    override fun presenceChanged(presence: Presence) {
        val entry = getEntry(presence.from.asBareJid())
        val freshPresence = getPresence(presence.from.asBareJid())
        val name = entry.name

        friendList.put(name, getFriendEntityFromPresence(freshPresence, name))
        notifyListenersDataChange(false)
    }

    private fun notifyListenersDataChange(isFreshData: Boolean) {
        val friendEntityList = friendList.map { it.value }
        changeListener.forEach { it.onDataChanged(isFreshData, friendEntityList) }
    }

    fun addOnFriendChangeListener(listener: IFriendChangeListener) {
        if (this.changeListener.contains(listener)) {
            throw IllegalStateException("This Listener was already added")
        }
        this.changeListener.add(listener)
        if (initialized) {
            notifyListenersDataChange(true)
        }
    }

    fun removeOnFriendChangeListener(listener: IFriendChangeListener) {
        if (!this.changeListener.contains(listener)) {
            throw IllegalStateException("This Listener was already removed")
        }
        this.changeListener.remove(listener)
    }

    fun onFreshDataRequested() {
        if(initialized){
            notifyListenersDataChange(true)
        }else{
            Timber.e("You requested fresh Data before the RosterManager is ready")
        }
    }

    fun getUpdatedFriendEntityByName(friendName: String): FriendEntity? {
        return if(friendList.containsKey(friendName)){
            friendList[friendName]
        }else{
            null
        }
    }
}