package ru.spbau.roguelike.ui

import com.googlecode.lanterna.input.KeyType
import ru.spbau.roguelike.controller.Turn
import kotlin.system.exitProcess

/**
 * Class for reading user input during game.
 *
 * Has methods that allow read user's input only during his/her turn
 */
class ConsoleInputReader(private val lanterna: Lanterna) {
    /**
     * Reads user's turn.
     *
     * Ignores all input thar was done before this function call
     */
    fun readTurn(): Turn {
        lanterna.ignoreAllPreviousInput()
        while (true) {
            val input = lanterna.readInput()
            when (input.keyType) {
                KeyType.ArrowDown  -> return Turn.MOVEMENT_DOWN
                KeyType.ArrowUp    -> return Turn.MOVEMENT_UP
                KeyType.ArrowLeft  -> return Turn.MOVEMENT_LEFT
                KeyType.ArrowRight -> return Turn.MOVEMENT_RIGHT
                KeyType.EOF -> exitProcess(0)
                else -> {
                    // ignore
                }
            }
        }
    }
}