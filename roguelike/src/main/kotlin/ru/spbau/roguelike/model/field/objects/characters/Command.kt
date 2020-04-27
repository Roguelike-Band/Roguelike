package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor

sealed class Command {
    abstract fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo)
}

class MoveCommand(val stepTo: Coordinates) : Command() {
    override fun execute(character: Character, movementExecutor: MovementExecutor, fieldInfo: FieldInfo) {
        movementExecutor.executeMove(character, stepTo, fieldInfo)
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
