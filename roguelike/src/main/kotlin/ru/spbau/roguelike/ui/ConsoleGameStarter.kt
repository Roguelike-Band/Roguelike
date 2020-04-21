package ru.spbau.roguelike.ui

import ru.spbau.roguelike.controller.ConsoleDisplayController
import ru.spbau.roguelike.controller.ConsoleReaderController
import ru.spbau.roguelike.model.logic.Logic
import java.io.IOException
import java.lang.IllegalArgumentException

/**
 * Class for starting game from console
 */
class ConsoleGameStarter(private val lanterna: Lanterna) {
    private var fieldFileName: String? = null

    /**
     * Starts game.
     *
     * If something gone wrong, shows error message
     */
    fun start() {
        lanterna.prepareGame()
        val consoleUIOutput = ConsoleUIOutput(lanterna)
        val consoleInputReader = ConsoleInputReader(lanterna)

        val consoleDisplayController = ConsoleDisplayController(consoleUIOutput)
        val consoleReaderController = ConsoleReaderController(consoleInputReader)

        val logic: Logic
        try {
            logic = Logic(consoleDisplayController, consoleReaderController, fieldFileName)
        } catch(e: IllegalArgumentException) {
            lanterna.printErrorMessage("Wrong field format")
            return
        } catch (e: IOException) {
            lanterna.printErrorMessage("Field cannot be loaded")
            return
        }
        logic.gameLoop()
        lanterna.createMenu(ConsoleGameStarter(lanterna))
        lanterna.refreshScreen()
    }

    /** Sets file from which logic should read start field */
    fun setFieldFileName(fileName: String?) {
        fieldFileName = fileName
    }
}