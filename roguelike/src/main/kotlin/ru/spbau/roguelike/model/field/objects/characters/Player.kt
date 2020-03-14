package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType

class Player(
    fieldInfo: FieldInfo,
    private val readerController: ReaderController
) : Character(fieldInfo) {

    override val objectType = FieldObjectType.PLAYER

    companion object {
        const val PLAYER_START_VISION = 5
    }

    init {
        setVisibleNeighbourhood()
    }

    private fun setVisibleNeighbourhood() {
        for (rowDiff in -PLAYER_START_VISION..PLAYER_START_VISION) {
            for (columnDiff in -PLAYER_START_VISION..PLAYER_START_VISION) {
                val row = rowDiff + fieldInfo.coordinates.row
                val column = columnDiff + fieldInfo.coordinates.column
                val coordinates = Coordinates(row, column)
                if (fieldInfo.isGood(coordinates)) {
                    fieldInfo.makeCellVisible(coordinates)
                }
            }
        }
    }

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    override fun doTurn() {
        val turn = readerController.readTurn()
        if (turn in arrayOf(Turn.MOVEMENT_LEFT, Turn.MOVEMENT_RIGHT, Turn.MOVEMENT_UP, Turn.MOVEMENT_DOWN)) {
            doMovementTurn(turn)
        } else {
            TODO("Do equipment turn")
        }
        setVisibleNeighbourhood()
    }

    private fun doMovementTurn(turn: Turn) {
        val coordinates = fieldInfo.coordinates
        val newCoordinates = when (turn) {
            Turn.MOVEMENT_DOWN    -> Coordinates(coordinates.row + 1, coordinates.column)
            Turn.MOVEMENT_UP      -> Coordinates(coordinates.row - 1, coordinates.column)
            Turn.MOVEMENT_LEFT    -> Coordinates(coordinates.row, coordinates.column - 1)
            Turn.MOVEMENT_RIGHT   -> Coordinates(coordinates.row, coordinates.column + 1)
            else -> throw IllegalArgumentException("Not movement turn")
        }
        if (fieldInfo.isGood(newCoordinates)) {
            val stepResult = fieldInfo[newCoordinates].onStep(this)
            if (stepResult == StepResult.STEP_SHOULD_BE_DONE) {
                fieldInfo.moveTo(newCoordinates)
            }
        } else {
            // TODO
        }
    }
}