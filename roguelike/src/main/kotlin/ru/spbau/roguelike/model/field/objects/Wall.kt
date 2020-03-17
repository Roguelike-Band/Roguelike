package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Object on a field that nobody can step on */
@Serializable
class Wall : FieldObject() {
    override val objectType = FieldObjectType.WALL

    override fun onStep(character: Character): StepResult {
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }
}