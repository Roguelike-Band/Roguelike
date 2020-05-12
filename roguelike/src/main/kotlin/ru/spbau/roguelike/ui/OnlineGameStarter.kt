package ru.spbau.roguelike.ui

import ru.spbau.roguelike.controller.ConsoleDisplayController
import ru.spbau.roguelike.controller.ConsoleReaderController

class OnlineGameStarter(private val lanterna: Lanterna) {
    fun start() {
        lanterna.prepareGame()

        val status = UIStatus()
        val consoleUIOutput = ConsoleUIOutput(lanterna, status)
        val consoleInputReader = ConsoleInputReader(lanterna, status, consoleUIOutput)

        val consoleDisplayController = ConsoleDisplayController(consoleUIOutput)
        val consoleReaderController = ConsoleReaderController(consoleInputReader)

        // TODO

        lanterna.createMenu(ConsoleGameStarter(lanterna), OnlineGameSelection(lanterna))
        lanterna.refreshScreen()
    }
}