package com.lolchat.myanmarking.myanchat.network.xmpp

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.EntityBareJid

class RiotChatManager : IncomingChatMessageListener, OutgoingChatMessageListener {
    private lateinit var chatManager: ChatManager

    fun init(conn: AbstractXMPPConnection){
        if (!(conn.isConnected && conn.isAuthenticated))
            throw IllegalStateException()

        this.chatManager = ChatManager.getInstanceFor(conn)
        this.chatManager.addIncomingListener(this)
        this.chatManager.addOutgoingListener(this)
    }

    override fun newIncomingMessage(from: EntityBareJid?, message: Message?, chat: Chat?) {

    }

    override fun newOutgoingMessage(to: EntityBareJid?, message: Message?, chat: Chat?) {

    }
}