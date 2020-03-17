package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.FieldObject

/** Any alive character on a field */
abstract class Character : FieldObject() {
    /** Method that is called by logic to make a turn */
    abstract fun doTurn(fieldInfo: FieldInfo)
}
