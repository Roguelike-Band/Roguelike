package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType

/** User's character */
@Serializable
class Player : Character() {
    companion object {
        const val PLAYER_START_VISION = 5
    }

    override val vision: Int = PLAYER_START_VISION

    override val objectType = FieldObjectType.PLAYER
    @Transient private lateinit var readerController: ReaderController

    fun initializeReaderController(readerController: ReaderController) {
        this.readerController = readerController
    }

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        @Suppress("UNREACHABLE_CODE", "ControlFlowWithEmptyBody")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    /** Reads input from `readerController` and does action */
    override fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        val turn = readerController.readTurn()
        when (turn) {
            Turn.MOVEMENT_LEFT, Turn.MOVEMENT_RIGHT, Turn.MOVEMENT_UP, Turn.MOVEMENT_DOWN ->
                doMovementTurn(turn, fieldInfo, movementExecutor)
            Turn.PUT_ON_EQUIPMENT, Turn.TAKE_OFF_EQUIPMENT -> TODO("Do equipment turn")
        }
    }

    private fun doMovementTurn(turn: Turn, fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        val coordinates = fieldInfo.coordinates
        val newCoordinates = when (turn) {
            Turn.MOVEMENT_DOWN    -> Coordinates(coordinates.row + 1, coordinates.column)
            Turn.MOVEMENT_UP      -> Coordinates(coordinates.row - 1, coordinates.column)
            Turn.MOVEMENT_LEFT    -> Coordinates(coordinates.row, coordinates.column - 1)
            Turn.MOVEMENT_RIGHT   -> Coordinates(coordinates.row, coordinates.column + 1)
            else -> throw IllegalArgumentException("Not movement turn")
        }
        movementExecutor.executeMove(this, newCoordinates, fieldInfo)
    }
}
