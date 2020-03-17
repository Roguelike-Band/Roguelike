package ru.spbau.roguelike.ui

class MainMenu(private val lanterna: Lanterna) {

    fun start() {
        lanterna.createMenu(ConsoleGameStarter(lanterna))
        lanterna.refreshScreen()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MainMenu(Lanterna()).start()
        }
    }
}