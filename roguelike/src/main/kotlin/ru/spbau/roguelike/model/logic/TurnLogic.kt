package ru.spbau.roguelike.model.logic

/** Logic of epoch of turns */
class TurnLogic(private val gameInfo: GameInfo) {
    /** Asks all characters to do their turn */
    fun doTurn() {
        gameInfo.character.doTurn(gameInfo.fieldInfo, gameInfo.movementExecutor)
    }
}
