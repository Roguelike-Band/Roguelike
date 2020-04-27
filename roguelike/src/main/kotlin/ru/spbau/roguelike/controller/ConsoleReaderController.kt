package ru.spbau.roguelike.controller

import ru.spbau.roguelike.controller.turncommands.UserCommand
import ru.spbau.roguelike.ui.ConsoleInputReader

/** Controller for console reading */
class ConsoleReaderController(private val consoleInputReader: ConsoleInputReader) : ReaderController {
    override fun readTurn(): UserCommand {
        return consoleInputReader.readTurn()
    }
}