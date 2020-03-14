package ru.spbau.roguelike.model.field.objects

import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

object InvisibleCell : FieldObject() {
    override val objectType = FieldObjectType.INVISIBLE_CELL

    override fun onStep(character: Character): StepResult {
        throw IllegalStateException("On step should not be called on invisible cell")
    }
}