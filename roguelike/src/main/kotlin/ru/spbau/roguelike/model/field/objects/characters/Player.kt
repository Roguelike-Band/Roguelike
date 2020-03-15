package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType

@Serializable
class Player : Character() {

    override val objectType = FieldObjectType.PLAYER
    @Transient private lateinit var readerController: ReaderController

    companion object {
        const val PLAYER_START_VISION = 5
    }

    fun initializeReaderController(readerController: ReaderController) {
        this.readerController = readerController
    }

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    override fun doTurn(fieldInfo: FieldInfo) {
        val turn = readerController.readTurn()
        when (turn ) {
            Turn.MOVEMENT_LEFT, Turn.MOVEMENT_RIGHT, Turn.MOVEMENT_UP, Turn.MOVEMENT_DOWN ->
                doMovementTurn(turn, fieldInfo)
            Turn.PUT_ON_EQUIPMENT, Turn.TAKE_OFF_EQUIPMENT ->
                TODO("Do equipment turn")
        }
        fieldInfo.setVisibleNeighbourhood()
    }

    private fun doMovementTurn(turn: Turn, fieldInfo: FieldInfo) {
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