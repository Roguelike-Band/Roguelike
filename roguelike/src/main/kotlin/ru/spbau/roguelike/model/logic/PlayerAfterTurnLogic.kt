package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.model.field.FieldInfo

/**
 * All the logic that is done when epoch of turns is finished.
 *
 * I.e. refreshing player's ui and generating new monsters
 */
class PlayerAfterTurnLogic(
        private val fieldInfo: FieldInfo,
        private val displayController: DisplayController
) : AfterTurnLogic {
    override fun refreshPlayerUI() {
        displayController.refreshGameField(fieldInfo)
    }
}