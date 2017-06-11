package com.lolchat.myanmarking.myanchat.io.enums

enum class GameQueueConfigId(
        val queueType: Int,
        val qeueuName: String

){
    CUSTOM(0, "Custom game"),
    NORMAL_3x3(8, "Normal 3v3"),
    NORMAL_5x5_BLIND(2, "Normal 5v5 Blind"),
    NORMAL_5x5_DRAFT(14, "Normal 5v5 Draft"),
    RANKED_SOLO_5x5(4, "Ranked Solo 5v5"),
    RANKED_FLEX_TT(9, "Ranked Flex TT"),
    RANKED_TEAM_5x5(42, "Ranked Team 5v5"),
    ODIN_5x5_BLIND(16, "Dominion 5v5 Blind"),
    ODIN_5x5_DRAFT(17, "Dominion 5v5 Draft"),
    BOT_ODIN_5x5(25, "Dominion Coop vs AI"),
    BOT_5x5_INTRO(31, "SR Coop vs AI Intro Bot"),
    BOT_5x5_BEGINNER(32, "SR Coop vs AI Beginner Bot"),
    BOT_5x5_INTERMEDIATE(33, "Historical SR Coop vs AI Intermediate Bot"),
    BOT_TT_3x3(52, "TT Coop vs AI"),
    GROUP_FINDER_5x5(61, "Team Builder"),
    ARAM_5x5(65, "ARAM 5x5"),
    ONEFORALL_5x5(70, "One for All 5x5"),
    FIRSTBLOOD_1x1(72, "Snowdown Showdown 1v1"),
    FIRSTBLOOD_2x2(73, "Snowdown Showdown 2v2"),
    SR_6x6(75, "SR 6x6 Hexakill"),
    URF_5x5(76, "URF"),
    ONEFORALL_MIRRORMODE_5x5(78, "OneForAll Mirror"),
    BOT_URF_5x5(83, "URF AI"),
    NIGHTMARE_BOT_5x5_RANK1(91, "Doom Bots Rank 1"),
    NIGHTMARE_BOT_5x5_RANK2(92, "Doom Bots Rank 2"),
    NIGHTMARE_BOT_5x5_RANK5(93, "Doom Bots Rank 5"),
    ASCENSION_5x5(96, "Ascension 5x5"),
    HEXAKILL(98, "TT 6x6 Hexakill"),
    BILGEWATER_ARAM_5x5(100, "Butcher's Bridge"),
    KING_PORO_5x5(300, "Poro King 5x5"),
    COUNTER_PICK(310, "Nemesis"),
    BILGEWATER_5x5(313, "Black Market Brawlers 5x5"),
    SIEGE(315, "Nexus Siege"),
    DEFINITELY_NOT_DOMINION_5x5(317, "Definitely Not Dominion"),
    ARURF_5X5(318, "ARURF"),
    ARSR_5x5(325, "All Random SR"),
    TEAM_BUILDER_DRAFT_UNRANKED_5x5(400, "5v5 Draft Pick"),

    TEAM_BUILDER_RANKED_SOLO(420, "Ranked Solo Team Builder"),
    TB_BLIND_SUMMONERS_RIFT_5x5(430, "SR 5v5 Blind Pick"),
    RANKED_FLEX_SR(440, "Ranked Flex SR"),
    ASSASSINATE_5x5(600, "Blood Hunt Assassin"),
    DARKSTAR_3x3(610, "Darkstar");

    companion object{
        fun get(str: String): GameQueueConfigId?{
            return GameQueueConfigId.values().find {
                it.name.toLowerCase() == str.toLowerCase()
            }
        }
    }
}