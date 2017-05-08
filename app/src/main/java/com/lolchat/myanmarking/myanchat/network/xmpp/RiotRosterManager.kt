package com.lolchat.myanmarking.myanchat.network.xmpp

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

class RiotRosterManager : RosterListener, RosterEntries {

    private val friendList: MutableMap<Jid, Friend> = hashMapOf()
    private var initedFriendsList: Boolean = false
    private lateinit var roster: Roster
    private var callback: IRosterManager? = null

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
                    .map { Friend(it.value.name, it.value.presence) }
                    .sortedBy {
                        if (it.presence.isAvailable) 0
                        else 1
                    }
                    .toList()
        }.subscribeOn(Schedulers.io())
    }

    override fun entriesDeleted(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            friendList.remove(jid)
        }
    }

    override fun entriesAdded(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            val entry = roster.getEntry(jid.asBareJid())
            val bestPresence = roster.getPresence(jid.asBareJid())

            if (!friendList.containsKey(jid)) {
                friendList.put(jid, Friend(entry.name, bestPresence))
            }
        }
    }

    override fun entriesUpdated(addresses: MutableCollection<Jid>) {
        addresses.forEach { jid ->
            val entry = roster.getEntry(jid.asBareJid())
            val bestPresence = roster.getPresence(jid.asBareJid())

            if (friendList.containsKey(jid)) {
                friendList.remove(jid)
            }
            friendList.put(jid, Friend(entry.name, bestPresence))
        }
    }

    override fun presenceChanged(presence: Presence) {
        val jid = presence.from.asBareJid()

        val entry = roster.getEntry(jid.asBareJid()).name
        val bestPresence = roster.getPresence(jid)

        if (friendList.containsKey(jid)) {
            friendList[jid] = Friend(
                    entry,
                    bestPresence
            )
        }
    }
}