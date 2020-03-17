package ru.spbau.roguelike.controller

import ru.spbau.roguelike.ui.ConsoleInputReader

/** Controller for console reading */
class ConsoleReaderController(private val consoleInputReader: ConsoleInputReader) : ReaderController {
    override fun readTurn(): Turn {
        return consoleInputReader.readTurn()
    }
}