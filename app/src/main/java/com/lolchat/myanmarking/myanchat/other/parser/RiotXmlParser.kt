package com.lolchat.myanmarking.myanchat.other.parser

import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING
import org.jivesoftware.smack.packet.Presence
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import timber.log.Timber
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class RiotXmlParser {

    companion object{
        const val STATUS_MSG = "statusMsg"
        const val PROFILE_ICON = "profileIcon"
        const val GAME_STATUS = "gameStatus"

        fun buildCustomAttrFromStatusMessage(presence: Presence): Element? {
            presence.status ?: return null

            val factory = DocumentBuilderFactory.newInstance()
            val builder: DocumentBuilder
            try {
                builder = factory.newDocumentBuilder()
            } catch (e: ParserConfigurationException) {
                Timber.e(e)
                return null
            }

            val document: Document
            try {
                document = builder.parse(InputSource(StringReader(presence.status)))
            } catch (e: SAXException) {
                Timber.e(e)
                return null
            } catch (e: IOException) {
                Timber.e(e)
                return null
            }

            return document.documentElement
        }

        fun getStringFromXmlTag(tagName: String, rootElement: Element?): String {
            if (rootElement == null)
                return EMPTY_STRING
            else {
                val list = rootElement.getElementsByTagName(tagName)
                if (list != null && list.length > 0) {
                    val subList = list.item(0).childNodes

                    if (subList != null && subList.length > 0) {
                        return subList.item(0).nodeValue
                    }
                }
                return EMPTY_STRING
            }
        }
    }
}