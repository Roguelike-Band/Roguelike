package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.EquipmentNavigatorMove
import ru.spbau.roguelike.controller.Turn

class Logic {
    private val turnLogic = TurnLogic()
    private val afterTurnLogic = AfterTurnLogic()

    fun doTurn(turn: Turn) {
        turnLogic.doTurn(turn)
        TODO("Not implemented")
    }

    fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        TODO("Not implemented")
    }
}