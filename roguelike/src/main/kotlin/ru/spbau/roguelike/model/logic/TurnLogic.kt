package ru.spbau.roguelike.model.logic

/** Logic of epoch of turns */
class TurnLogic(private val gameInfo: GameInfo) {
    /** Asks all characters to do their turn */
    fun doTurn() {
        gameInfo.player.doTurn(gameInfo.fieldInfo)
    }
}