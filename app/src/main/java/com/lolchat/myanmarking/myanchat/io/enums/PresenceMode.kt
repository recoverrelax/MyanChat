package com.lolchat.myanmarking.myanchat.io.enums

import com.lolchat.myanmarking.myanchat.R

enum class PresenceMode(
        val color: Int
){
    AVAILABLE(R.color.md_green_700),
    CHAT(R.color.md_green_700),
    AWAY(R.color.md_yellow_700),
    DND(R.color.md_deep_orange_900),
    XA(R.color.md_deep_orange_700),
    OFFLINE(R.color.black);
}