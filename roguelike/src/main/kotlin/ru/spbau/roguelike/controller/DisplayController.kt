package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.CharacterView

/** Interface for refreshing information from model to user */
interface DisplayController {
    /** Shows changes on field to user */
    fun refreshGameField(field: DisplayFieldInfo, character: CharacterView)
}