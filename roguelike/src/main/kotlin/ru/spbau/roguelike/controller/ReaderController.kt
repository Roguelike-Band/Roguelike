package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.logic.Logic

class ReaderController {
    private val logic = Logic()

    fun onTurn(turn: Turn) {
        logic.doTurn(turn)
    }

    fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        logic.onEquipmentNavigatorMove(equipmentNavigatorMove)
    }
}