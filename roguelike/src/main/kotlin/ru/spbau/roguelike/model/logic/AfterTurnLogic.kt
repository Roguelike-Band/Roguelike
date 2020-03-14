package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController

class AfterTurnLogic(
    private val gameInfo: GameInfo,
    private val displayController: DisplayController
) {
    fun refreshPlayerUI() {
        displayController.refreshGameField(gameInfo.player.fieldInfo)
    }

    fun generateNewMonsters() {
        TODO("Not implemented")
    }
}