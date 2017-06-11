package com.lolchat.myanmarking.myanchat.other.parser

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus
import com.lolchat.myanmarking.myanchat.io.enums.PresenceMode
import com.lolchat.myanmarking.myanchat.io.enums.RankedLeagueTierDivision
import com.lolchat.myanmarking.myanchat.io.model.xmpp.ChampionId
import com.lolchat.myanmarking.myanchat.io.model.xmpp.PlayingData
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

    companion object {
        const val STATUS_MSG = "statusMsg"
        const val PROFILE_ICON = "profileIcon"
        const val GAME_STATUS = "gameStatus"
        const val RANKED_LEAGUE_TIER = "rankedLeagueTier"
        const val RANKED_LEAGUE_DIVISION = "rankedLeagueDivision"
        const val GAME_QUEUE_TYPE = "gameQueueType"
        const val CHAMPION_ID = "championId"
        const val TIMESTAMP = "timeStamp"

        fun getProfileIcon(rootElement: Element?): String
                = getStringFromXmlTag(RiotXmlParser.PROFILE_ICON, rootElement)

        fun getStatusMessage(rootElement: Element?): String
                = RiotXmlParser.getStringFromXmlTag(RiotXmlParser.STATUS_MSG, rootElement)

        fun getGameStatus(rootElement: Element?): GameStatus{
            val statusStanza = RiotXmlParser.getStringFromXmlTag(RiotXmlParser.GAME_STATUS, rootElement)
            return GameStatus.getByXmppStanza(statusStanza)
        }

        fun getTime(rootElement: Element?): Long{
            val cid = RiotXmlParser.getStringFromXmlTag(TIMESTAMP, rootElement)
            val time: Long = try {
                cid.toLong()
            }catch (e: Exception){
                -1L
            }
            return time
        }

        fun getPlayingData(rootElement: Element?, champ: ChampionId, gameStatus: GameStatus, isOnline: Boolean): PlayingData?{
            if(!isOnline) return null

            if(gameStatus == GameStatus.CHAMPION_SELECT || gameStatus == GameStatus.INGAME){
                return PlayingData(gameStatus, champ, getTime(rootElement))
            }
            return null
        }

        fun getChampionId(rootElement: Element?): Int{
            val cid = RiotXmlParser.getStringFromXmlTag(CHAMPION_ID, rootElement)
            val championId: Int = try {
                cid.toInt()
            }catch (e: Exception){
                -1
            }
            return championId
        }

        fun getRankedLeagueTierAnddivision(rootElement: Element?, presence: Presence): RankedLeagueTierDivision{
            val tier = RiotXmlParser.getStringFromXmlTag(RANKED_LEAGUE_TIER, rootElement)
            val division = RiotXmlParser.getStringFromXmlTag(RANKED_LEAGUE_DIVISION, rootElement)
            return if(presence.isAvailable) RankedLeagueTierDivision.getIconDrawableByLeagueAndTier(tier, division)
            else RankedLeagueTierDivision.NONE
        }

        fun getPresenceMode(rootElement: Element?, presence: Presence): PresenceMode{
            return if (presence.isAvailable) {
                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                when (presence.mode) {
                    Presence.Mode.chat -> PresenceMode.CHAT
                    Presence.Mode.available -> PresenceMode.AVAILABLE
                    Presence.Mode.away -> PresenceMode.AWAY
                    Presence.Mode.xa -> PresenceMode.XA
                    Presence.Mode.dnd -> PresenceMode.DND
                }
            } else {
                PresenceMode.OFFLINE
            }
        }

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

        private fun getStringFromXmlTag(tagName: String, rootElement: Element?): String {
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