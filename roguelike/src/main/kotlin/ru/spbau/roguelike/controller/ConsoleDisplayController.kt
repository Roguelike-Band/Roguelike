package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList
import ru.spbau.roguelike.ui.ConsoleUIOutput

class ConsoleDisplayController(
    private val consoleUIOutput: ConsoleUIOutput
) : DisplayController {

    override fun refreshGameField(field: FieldInfo) {
        consoleUIOutput.refreshGameField(field)
    }

    override fun refreshEquipmentList(equipmentList: EquipmentList) {
        TODO("Not implemented")
    }

    override fun onGameIsFinished() {
        TODO("Not implemented")
    }
}