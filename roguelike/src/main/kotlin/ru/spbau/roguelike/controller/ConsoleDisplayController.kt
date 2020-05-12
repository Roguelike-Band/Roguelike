package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.CharacterView
import ru.spbau.roguelike.ui.ConsoleUIOutput

/** Controller for console showing */
class ConsoleDisplayController(
    private val consoleUIOutput: ConsoleUIOutput
) : DisplayController {

    override fun refreshGameField(field: DisplayFieldInfo, character: CharacterView) {
        consoleUIOutput.refreshGameField(field, character)
    }
}