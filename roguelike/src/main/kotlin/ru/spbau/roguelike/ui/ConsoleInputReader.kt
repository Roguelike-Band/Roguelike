package ru.spbau.roguelike.ui

import com.googlecode.lanterna.input.KeyType
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
import ru.spbau.roguelike.model.field.objects.characters.PutOnEquipmentCommand
import ru.spbau.roguelike.model.field.objects.characters.TakeOffEquipmentCommand
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
    fun readCommand(fieldInfo: FieldInfo): Command {
        lanterna.ignoreAllPreviousInput()
        while (true) {
            val input = lanterna.readInput()
            when (input.keyType) {
                KeyType.ArrowDown  -> return MoveCommand(Turn.MOVEMENT_DOWN, fieldInfo.coordinates)
                KeyType.ArrowUp    -> return MoveCommand(Turn.MOVEMENT_UP, fieldInfo.coordinates)
                KeyType.ArrowLeft  -> return MoveCommand(Turn.MOVEMENT_LEFT, fieldInfo.coordinates)
                KeyType.ArrowRight -> return MoveCommand(Turn.MOVEMENT_RIGHT, fieldInfo.coordinates)
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
                        'w' -> return PutOnEquipmentCommand(status.equipmentCursor)
                        's' -> return TakeOffEquipmentCommand(status.equipmentCursor)
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