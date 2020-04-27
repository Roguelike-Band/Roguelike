package ru.spbau.roguelike.model.field.objects.characters.player

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.controller.turncommands.EquipmentUserCommand
import ru.spbau.roguelike.controller.turncommands.MoveUserCommand
import ru.spbau.roguelike.controller.turncommands.UserCommand
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.*

/**
 * Strategy for player's turns.
 * Waits for input and then moves as instructed by player.
 */
@Serializable
class PlayerStrategy(private val readerController: ReaderController) : Strategy {
    override fun generateStep(character: Character, fieldInfo: FieldInfo): Command {
        val turn = readerController.readTurn()
        return when (turn) {
            is MoveUserCommand -> generateMovementTurn(turn, fieldInfo)
            is EquipmentUserCommand -> generateEquipmentTurn(turn)
        }
    }

    private fun generateMovementTurn(turn: UserCommand, fieldInfo: FieldInfo): MoveCommand {
        val coordinates = fieldInfo.coordinates
        return MoveCommand(
                when (turn.commandType) {
                    Turn.MOVEMENT_DOWN    -> Coordinates(coordinates.row + 1, coordinates.column)
                    Turn.MOVEMENT_UP      -> Coordinates(coordinates.row - 1, coordinates.column)
                    Turn.MOVEMENT_LEFT    -> Coordinates(coordinates.row, coordinates.column - 1)
                    Turn.MOVEMENT_RIGHT   -> Coordinates(coordinates.row, coordinates.column + 1)
                    else -> throw IllegalArgumentException("Not movement turn")
                }
        )
    }

    private fun generateEquipmentTurn(turn: EquipmentUserCommand): Command {
        return when (turn.commandType) {
            Turn.TAKE_OFF_EQUIPMENT -> TakeOffEquipmentCommand(turn.index)
            Turn.PUT_ON_EQUIPMENT -> PutOnEquipmentCommand(turn.index)
            else -> throw IllegalArgumentException("Not equipment turn")
        }
    }
}