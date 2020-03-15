package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

@Serializable
class InvisibleCell : FieldObject() {
    override val objectType = FieldObjectType.INVISIBLE_CELL

    override fun onStep(character: Character): StepResult {
        throw IllegalStateException("On step should not be called on invisible cell")
    }
}