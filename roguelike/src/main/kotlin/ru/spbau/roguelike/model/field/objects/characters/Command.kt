package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor

/** Class represents character action */
sealed class Command {

    /**  Executes command */
    abstract fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo)
}

/** Command for player movement */
class MoveCommand(val stepTo: Coordinates) : Command() {
    constructor(turn: Turn, coordinates: Coordinates) : this(
        when (turn) {
            Turn.MOVEMENT_DOWN  -> Coordinates(coordinates.row + 1, coordinates.column)
            Turn.MOVEMENT_UP    -> Coordinates(coordinates.row - 1, coordinates.column)
            Turn.MOVEMENT_LEFT  -> Coordinates(coordinates.row, coordinates.column - 1)
            Turn.MOVEMENT_RIGHT -> Coordinates(coordinates.row, coordinates.column + 1)
    })
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        movementExecutor.executeMove(character, stepTo, fieldInfo)
    }
}

/** Command for putting on player's equipment */
class PutOnEquipmentCommand(private val index: Int) : Command() {
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        character.putOnEquipment(index)
    }
}

/** Command for taking off player's equipment */
class TakeOffEquipmentCommand(private val index: Int) : Command() {
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        character.takeOffEquipment(index)
    }
}
