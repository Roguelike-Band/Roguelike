package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController

/**
 * All the logic that is done when epoch of turns is finished.
 *
 * I.e. refreshing player's ui and generating new monsters
 */
class PlayerAfterTurnLogic(
    private val gameInfo: GameInfo,
    private val displayController: DisplayController
) : AfterTurnLogic {
    override fun refreshPlayerUI() {
        displayController.refreshGameField(gameInfo.fieldInfo)
    }
}