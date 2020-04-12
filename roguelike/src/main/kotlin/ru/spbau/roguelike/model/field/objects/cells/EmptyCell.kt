package ru.spbau.roguelike.model.field.objects.cells

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Just an empty cell on a field */
@Serializable
class EmptyCell : FieldObject() {
    override val objectType = FieldObjectType.EMPTY_CELL
    @Transient override val canBeCharacterStartCell = true

    override fun onStep(character: Character): StepResult {
        return StepResult.STEP_SHOULD_BE_DONE
    }
}