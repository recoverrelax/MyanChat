package com.lolchat.myanmarking.myanchat.io.enums

enum class RiotServer(
        val serverName: String,
        val serverHost: String,
        val platformId: String,
        val region: String
){
    BR("Brazil", "chat.br.lol.riotgames.com", "BR1",  "br"),
    EUNE("Europe Nordic and East", "chat.eun1.riotgames.com", "EUN1",  "eune"),
    EUW("Europe West", "chat.euw1.lol.riotgames.com", "EUW1",  "euw"),
    KR("Korea", "chat.kr.lol.riotgames.com", "KR",  "kr"),
    LAN("Latin America North", "chat.la1.lol.riotgames.com", "LA1",  "lan"),
    LAS("Latin America South", "chat.la2.lol.riotgames.com", "LA2",  "las"),
    NA("North America", "chat.na1.lol.riotgames.com", "NA1",  "na"),
    OCE("Oceania", "chat.oc1.lol.riotgames.com", "OC1",  "oce"),
    RU("Russia", "chat.ru.lol.riotgames.com", "RU1",  "ru"),
    TR("Turkey", "chat.tr.lol.riotgames.com", "TR1",  "tr");
}