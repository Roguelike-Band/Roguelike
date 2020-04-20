package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.FieldObject

/** Any alive character on a field */
@Serializable
abstract class Character(protected var strategy: Strategy) : FieldObject() {
    abstract val vision: Int
    /** Method that is called by logic to make a turn */
    fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        val stepTo = strategy.generateStep(fieldInfo)
        movementExecutor.executeMove(this, stepTo, fieldInfo)
    }

    fun confuse() {
        TODO("NOT IMPLEMENTED")
    }
}
