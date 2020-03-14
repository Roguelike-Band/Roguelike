package ru.spbau.roguelike.model.field.objects

import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

object Wall : FieldObject() {
    override val objectType = FieldObjectType.WALL

    override fun onStep(character: Character): StepResult {
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }
}