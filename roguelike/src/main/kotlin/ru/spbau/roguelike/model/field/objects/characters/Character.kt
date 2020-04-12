package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.FieldObject

/** Any alive character on a field */
abstract class Character : FieldObject() {
    abstract val vision: Int
    /** Method that is called by logic to make a turn */
    abstract fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor)
}
