package ru.spbau.roguelike.model.field.objects

import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

object EmptyCell : FieldObject() {
    override val objectType = FieldObjectType.EMPTY_CELL
    override val canBeCharacterStartCell = true

    override fun onStep(character: Character): StepResult {
        return StepResult.STEP_SHOULD_BE_DONE
    }
}