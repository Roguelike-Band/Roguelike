package ru.spbau.roguelike

import ru.spbau.roguelike.controller.ConsoleDisplayController
import ru.spbau.roguelike.controller.ConsoleReaderController
import ru.spbau.roguelike.model.logic.Logic
import ru.spbau.roguelike.ui.ConsoleInputReader
import ru.spbau.roguelike.ui.ConsoleUIOutput
import ru.spbau.roguelike.ui.Lanterna


fun main() {
    val lanterna = Lanterna()

    val consoleUIOutput = ConsoleUIOutput(lanterna)
    val consoleInputReader = ConsoleInputReader(lanterna)

    val consoleDisplayController = ConsoleDisplayController(consoleUIOutput)
    val consoleReaderController = ConsoleReaderController(consoleInputReader)

    val logic = Logic(consoleDisplayController, consoleReaderController)

    logic.gameLoop()
}