package ru.spbau.roguelike.ui

/**
 * Class for showing main menu.
 *
 * Entry point of a game
 */
class MainMenu(private val lanterna: Lanterna) {

    fun start() {
        lanterna.createMenu(ConsoleGameStarter(lanterna), OnlineGameSelection(lanterna))
        lanterna.refreshScreen()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MainMenu(Lanterna()).start()
        }
    }
}