package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.model.field.FieldInfo

class PlayerAfterTurnLogic(
        private val fieldInfo: FieldInfo,
        private val displayController: DisplayController
) : AfterTurnLogic {
    override fun refreshPlayerUI() {
        displayController.refreshGameField(fieldInfo)
    }
}