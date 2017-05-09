package com.lolchat.myanmarking.myanchat.network.xmpp

import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.interfaces.IRosterManager
import com.lolchat.myanmarking.myanchat.io.model.xmpp.Friend
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntries
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.roster.RosterListener
import org.jxmpp.jid.Jid
import timber.log.Timber

class RiotRosterManager : RosterListener, RosterEntries {

    private val INFO_ENABLED = true
    private val friendList: MutableMap<Jid, Friend> = hashMapOf()
    private var initedFriendsList: Boolean = false
    private lateinit var roster: Roster
    private var callback: IRosterManager? = null

    private val changeListener: MutableList<IFriendChangeListener> = mutableListOf()

    fun init(conn: AbstractXMPPConnection, callback: IRosterManager) {
        if (!(conn.isConnected && conn.isAuthenticated))
            throw IllegalStateException()
        this.callback = callback
        this.roster = Roster.getInstanceFor(conn)
        roster.getEntriesAndAddListener(this, this)
    }

    override fun rosterEntries(rosterEntries: MutableCollection<RosterEntry>) {
        rosterEntries.forEach { entry ->
            val jid = entry.jid

            if (!friendList.containsKey(jid)) {
                friendList.put(jid, Friend(entry.name, roster.getPresence(jid)))
            }
        }
        this.callback?.onRosterReady()
        this.callback = null
        this.initedFriendsList = true
    }

    fun hasInnitedFriendsList(): Boolean = initedFriendsList

    fun getFriendListName(): Observable<List<Friend>> {
        return Observable.fromCallable {
            friendList
                    .map { it.value }
                    .sortedBy {
                        if (it.presence.isAvailable) 0
                        else 1
                    }
                    .toList()
        }.subscribeOn(Schedulers.io())
    }

    override fun entriesDeleted(addresses: MutableCollection<Jid>) {
        if(INFO_ENABLED) Timber.i("entriesDeleted: $addresses")
        val deletedEntries: MutableList<Friend> = mutableListOf()

        addresses.forEach { jid ->
            val friend = friendList.remove(jid)
            if(friend != null){
                deletedEntries.add(friend)
            }
        }
        changeListener.forEach { it.onFriendsRemoved(deletedEntries) }
    }

    override fun entriesAdded(addresses: MutableCollection<Jid>) {
        if(INFO_ENABLED) Timber.i("entriesAdded: $addresses")
        val addedEntries: MutableList<Friend> = mutableListOf()

        addresses.forEach { jid ->
            val entry = roster.getEntry(jid.asBareJid())
            val bestPresence = roster.getPresence(jid.asBareJid())

            if (!friendList.containsKey(jid)) {
                val friend = Friend(entry.name, bestPresence)
                friendList.put(jid, friend)
                addedEntries.add(friend)
            }
        }
        changeListener.forEach { it.onFriendsRemoved(addedEntries) }
    }

    override fun entriesUpdated(addresses: MutableCollection<Jid>) {
        if(INFO_ENABLED) Timber.i("entriesUpdated: $addresses")
        val updatedEntries: MutableList<Friend> = mutableListOf()

        addresses.forEach { jid ->
            val entry = roster.getEntry(jid.asBareJid())
            val bestPresence = roster.getPresence(jid.asBareJid())

            if (friendList.containsKey(jid)) {
                friendList.remove(jid)
            }
            val friend = Friend(entry.name, bestPresence)
            friendList.put(jid, friend)
            updatedEntries.add(friend)
        }
        changeListener.forEach { it.onFriendsChanged(updatedEntries) }
    }

    override fun presenceChanged(presence: Presence) {
        if(INFO_ENABLED) Timber.i("presenceChanged: $presence")
        val updatedEntries: MutableList<Friend> = mutableListOf()

        val jid = presence.from.asBareJid()

        val entry = roster.getEntry(jid).name
        val bestPresence = roster.getPresence(jid)

        if (friendList.containsKey(jid)) {
            val friend = Friend(
                    entry,
                    bestPresence
            )
            friendList[jid] = friend
            updatedEntries.add(friend)
        }
        changeListener.forEach { it.onFriendsChanged(updatedEntries) }
    }

    fun addOnFriendChangeListener(listener: IFriendChangeListener){
        if(this.changeListener.contains(listener)){
            throw IllegalStateException("This Listener was already added")
        }
        this.changeListener.add(listener)
    }

    fun removeOnFriendChangeListener(listener: IFriendChangeListener){
        if(!this.changeListener.contains(listener)){
            throw IllegalStateException("This Listener was already removed")
        }
        this.changeListener.remove(listener)
    }
}