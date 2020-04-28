package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor

abstract class Command {
    abstract fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo)
}

abstract class MoveCommand() : Command() {
    abstract val stepTo: Coordinates
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        movementExecutor.executeMove(character, stepTo, fieldInfo)
    }
}

class MoveToCoordinatesCommand(override val stepTo: Coordinates) : MoveCommand() {}

class MoveUserCommand(turn: Turn, coordinates: Coordinates) : MoveCommand() {
    override val stepTo = when (turn) {
        Turn.MOVEMENT_DOWN -> Coordinates(coordinates.row + 1, coordinates.column)
        Turn.MOVEMENT_UP -> Coordinates(coordinates.row - 1, coordinates.column)
        Turn.MOVEMENT_LEFT -> Coordinates(coordinates.row, coordinates.column - 1)
        Turn.MOVEMENT_RIGHT -> Coordinates(coordinates.row, coordinates.column + 1)
    }
}

class PutOnEquipmentCommand(private val index: Int) : Command() {
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        character.putOnEquipment(index)
    }
}

class TakeOffEquipmentCommand(private val index: Int) : Command() {
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        character.takeOffEquipment(index)
    }
}
