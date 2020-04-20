package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo

interface Strategy {
    fun generateStep(fieldInfo: FieldInfo): Coordinates
}