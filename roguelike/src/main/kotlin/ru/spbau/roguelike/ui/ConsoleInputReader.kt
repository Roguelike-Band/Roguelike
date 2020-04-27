package ru.spbau.roguelike.ui

import com.googlecode.lanterna.input.KeyType
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.controller.turncommands.UserCommand
import ru.spbau.roguelike.controller.turncommands.EquipmentUserCommand
import ru.spbau.roguelike.controller.turncommands.MoveUserCommand
import kotlin.system.exitProcess

/**
 * Class for reading user input during game.
 *
 * Has methods that allow read user's input only during his/her turn
 */
class ConsoleInputReader(
        private val lanterna: Lanterna,
        private val status: UIStatus,
        private val consoleUIOutput: ConsoleUIOutput
) {
    /**
     * Reads user's turn.
     *
     * Ignores all input thar was done before this function call
     */
    fun readTurn(): UserCommand {
        lanterna.ignoreAllPreviousInput()
        while (true) {
            val input = lanterna.readInput()
            when (input.keyType) {
                KeyType.ArrowDown  -> return MoveUserCommand(Turn.MOVEMENT_DOWN)
                KeyType.ArrowUp    -> return MoveUserCommand(Turn.MOVEMENT_UP)
                KeyType.ArrowLeft  -> return MoveUserCommand(Turn.MOVEMENT_LEFT)
                KeyType.ArrowRight -> return MoveUserCommand(Turn.MOVEMENT_RIGHT)
                KeyType.Character -> {
                    when (input.character) {
                        'a' -> {
                            status.moveCursorLeft()
                            consoleUIOutput.refreshGameField(status.fieldInfo!!)
                        }
                        'd' -> {
                            status.moveCrsorRight()
                            consoleUIOutput.refreshGameField(status.fieldInfo!!)
                        }
                        'w' -> return EquipmentUserCommand(Turn.PUT_ON_EQUIPMENT, status.equipmentCursor)
                        's' -> return EquipmentUserCommand(Turn.TAKE_OFF_EQUIPMENT, status.equipmentCursor)
                    }
                }
                KeyType.EOF -> exitProcess(0)
                else -> {
                    // ignore
                }
            }
        }
    }
}