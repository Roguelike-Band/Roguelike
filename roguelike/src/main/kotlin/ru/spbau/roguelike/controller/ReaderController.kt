package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Command

/** Interface for model getting turns from user */
interface ReaderController {
    /** Returns user's new turn */
    fun readCommand(fieldInfo: FieldInfo): Command
}