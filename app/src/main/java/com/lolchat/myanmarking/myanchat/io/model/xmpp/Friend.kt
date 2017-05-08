package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.other.Parser.RiotXmlParser
import org.jivesoftware.smack.packet.Presence
import org.w3c.dom.Element

@Suppress("JoinDeclarationAndAssignment")
data class Friend(
    val name: String,
    val presence: Presence
){
    private val rootElement: Element?

    init {
        rootElement = RiotXmlParser.buildCustomAttrFromStatusMessage(presence)
    }

    fun getStatusMessage(): String = RiotXmlParser.getStringFromXmlTag(RiotXmlParser.STATUS_MSG, rootElement)
}