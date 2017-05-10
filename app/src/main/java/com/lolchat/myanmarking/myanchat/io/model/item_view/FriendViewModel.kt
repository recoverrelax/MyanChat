package com.lolchat.myanmarking.myanchat.io.model.item_view

import com.lolchat.myanmarking.myanchat.io.enums.GameStatus

data class FriendViewModel(
        var name: String,
        var sumIconUrl: String,
        var statusMessage: String,
        var presenceStatus: String,
        var isFriendOnline: Boolean,
        var gameStatus: GameStatus
){
    fun rebind(viewModel: FriendViewModel){
        this.name = viewModel.name
        this.sumIconUrl = viewModel.sumIconUrl
        this.gameStatus = viewModel.gameStatus
        this.statusMessage = viewModel.statusMessage
        this.presenceStatus = viewModel.presenceStatus
        this.isFriendOnline = viewModel.isFriendOnline
        this.gameStatus = viewModel.gameStatus
    }
}