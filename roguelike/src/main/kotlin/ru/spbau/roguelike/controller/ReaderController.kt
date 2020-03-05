package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.logic.Logic
import ru.spbau.roguelike.ui.ConsoleInputReader

class ReaderController {
    private val consoleInputReader = ConsoleInputReader()
    private val logic = Logic()

    fun onTurn(turn: Turn) {
        logic.doTurn(turn)
    }

    fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        logic.onEquipmentNavigatorMove(equipmentNavigatorMove)
    }
}