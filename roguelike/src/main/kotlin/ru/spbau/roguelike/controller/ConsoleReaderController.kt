package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.ui.ConsoleInputReader

/** Controller for console reading */
class ConsoleReaderController(private val consoleInputReader: ConsoleInputReader) : ReaderController {

    override fun readCommand(fieldInfo: FieldInfo): Command {
        return consoleInputReader.readCommand(fieldInfo)
    }
}