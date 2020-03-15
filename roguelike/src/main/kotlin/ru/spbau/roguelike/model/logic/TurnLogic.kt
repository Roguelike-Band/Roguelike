package ru.spbau.roguelike.model.logic

class TurnLogic(private val gameInfo: GameInfo) {
    fun doTurn() {
        gameInfo.player.doTurn(gameInfo.fieldInfo)
    }
}