package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

abstract class FieldObject {
    abstract val objectType: FieldObjectType
    open val canBeCharacterStartCell = false

    abstract fun onStep(character: Character): StepResult
}