package com.lolchat.myanmarking.myanchat.network.xmpp

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.XMPPConnection
import java.lang.Exception

class RiotConnManager : ConnectionListener {

    fun init(conn: AbstractXMPPConnection) {
        if (!(conn.isConnected && conn.isAuthenticated))
            throw IllegalStateException()

        ReconnectionManager.getInstanceFor(conn).apply {
            enableAutomaticReconnection()
            setReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.RANDOM_INCREASING_DELAY)
        }
        conn.addConnectionListener(this)
    }

    override fun connected(connection: XMPPConnection?) {

    }

    override fun connectionClosed() {

    }

    override fun connectionClosedOnError(e: Exception?) {

    }

    override fun reconnectionSuccessful() {

    }

    override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {

    }

    override fun reconnectionFailed(e: Exception?) {

    }

    override fun reconnectingIn(seconds: Int) {

    }
}