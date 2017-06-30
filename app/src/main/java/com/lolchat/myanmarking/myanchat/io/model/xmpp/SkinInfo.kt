package com.lolchat.myanmarking.myanchat.io.model.xmpp

import com.lolchat.myanmarking.myanchat.io.other.EMPTY_STRING

class SkinInfo(
        val skinName: String,
        val skinVariant: String
) {
    fun getComposedName(): String {
        return if(skinVariant == EMPTY_STRING){
            "${skinName}_$0"
        }else{
            "${skinName}_$skinVariant"
        }
    }
}