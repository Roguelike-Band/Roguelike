package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.player.Player
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList
import ru.spbau.roguelike.ui.ConsoleUIOutput

/** Controller for console showing */
class ConsoleDisplayController(
    private val consoleUIOutput: ConsoleUIOutput
) : DisplayController {

    override fun refreshGameField(field: DisplayFieldInfo, character: Player) {
        consoleUIOutput.refreshGameField(field, character)
    }
}