package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.other.Parser.RiotXmlParser
import org.jivesoftware.smack.packet.Presence
import org.jxmpp.jid.Jid
import org.w3c.dom.Element

@Suppress("JoinDeclarationAndAssignment")
class Friend(
        val name: String,
        val presence: Presence
){
    private val rootElement: Element?

    val statusMessage: String
    get() = RiotXmlParser.getStringFromXmlTag(RiotXmlParser.STATUS_MSG, rootElement)

    val profileIcon: String
    get() = RiotXmlParser.getStringFromXmlTag(RiotXmlParser.PROFILE_ICON, rootElement)

    init {
        rootElement = RiotXmlParser.buildCustomAttrFromStatusMessage(presence)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Friend

        if (presence.from != other.presence.from) return false

        return true
    }

    override fun hashCode(): Int {
        return presence.from.hashCode()
    }


}