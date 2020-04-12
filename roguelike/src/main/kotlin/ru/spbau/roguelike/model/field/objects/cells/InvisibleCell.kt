package ru.spbau.roguelike.model.field.objects.cells

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Cell that is invisible for a character. Used only by `FieldInfo` */
@Serializable
class InvisibleCell : FieldObject() {
    override val objectType = FieldObjectType.INVISIBLE_CELL

    override fun onStep(character: Character): StepResult {
        throw IllegalStateException("On step should not be called on invisible cell")
    }
}