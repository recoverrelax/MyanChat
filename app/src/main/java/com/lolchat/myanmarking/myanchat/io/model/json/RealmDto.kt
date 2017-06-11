package com.lolchat.myanmarking.myanchat.io.model.json
class RealmDto(
        val lg: String,
        val dd: String,
        val l: String,
        val n: Map<String, String>,
        val profileiconmax: Int,
        val v: String,
        val cdn: String,
        val css: String
){
    companion object{
        val DEFAULT = RealmDto(
                "7.10.1",
                "7.10.1",
                "en_GN",
                hashMapOf(
                        "summoner" to "7.10.1",
                        "map" to "7.10.1",
                        "champion" to "7.10.1",
                        "language" to "7.10.1",
                        "mastery" to "7.10.1",
                        "sticker" to "7.10.1",
                        "item" to "7.10.1",
                        "rune" to "7.10.1",
                        "profileicon" to "7.10.1"
                ),
                28,
                "7.10.1",
                "http://ddragon.leagueoflegends.com/cdn",
                "7.10.1"
        )
    }
}