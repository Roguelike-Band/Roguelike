package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.FieldObject

abstract class Character : FieldObject() {
    abstract fun doTurn(fieldInfo: FieldInfo)
}
