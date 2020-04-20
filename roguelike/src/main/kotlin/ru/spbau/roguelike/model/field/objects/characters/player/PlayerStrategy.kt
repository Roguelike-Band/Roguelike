package ru.spbau.roguelike.model.field.objects.characters.player

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy

@Serializable
class PlayerStrategy(private val readerController: ReaderController) : Strategy {
    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        val turn = readerController.readTurn()
        return generateMovementTurn(turn, fieldInfo)
    }

    private fun generateMovementTurn(turn: Turn, fieldInfo: FieldInfo): Coordinates {
        val coordinates = fieldInfo.coordinates
        return when (turn) {
            Turn.MOVEMENT_DOWN    -> Coordinates(coordinates.row + 1, coordinates.column)
            Turn.MOVEMENT_UP      -> Coordinates(coordinates.row - 1, coordinates.column)
            Turn.MOVEMENT_LEFT    -> Coordinates(coordinates.row, coordinates.column - 1)
            Turn.MOVEMENT_RIGHT   -> Coordinates(coordinates.row, coordinates.column + 1)
            else -> throw IllegalArgumentException("Not movement turn")
        }
    }
}