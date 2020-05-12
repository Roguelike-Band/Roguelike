package ru.spbau.roguelike.ui

import ru.spbau.roguelike.controller.ConsoleDisplayController
import ru.spbau.roguelike.controller.ConsoleReaderController
import ru.spbau.roguelike.net.client.GameConnection
import ru.spbau.roguelike.net.client.Logic

class OnlineGameStarter(
    private val lanterna: Lanterna,
    private val gameConnection: GameConnection
) {
    fun start() {
        lanterna.prepareGame()

        val status = UIStatus()
        val consoleUIOutput = ConsoleUIOutput(lanterna, status)
        val consoleInputReader = ConsoleInputReader(lanterna, status, consoleUIOutput)

        val consoleDisplayController = ConsoleDisplayController(consoleUIOutput)
        val consoleReaderController = ConsoleReaderController(consoleInputReader)

        val logic = Logic(consoleDisplayController, consoleReaderController, gameConnection)

        lanterna.createMenu(ConsoleGameStarter(lanterna), OnlineGameSelection(lanterna))
        lanterna.refreshScreen()
    }
}