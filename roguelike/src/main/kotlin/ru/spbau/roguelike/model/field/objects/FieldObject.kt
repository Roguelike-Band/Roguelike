package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Every cell on the field is inheritor of this class */
@Serializable
abstract class FieldObject {
    abstract val objectType: FieldObjectType

    @Transient
    open val canBeCharacterStartCell = false

    /**
     * Does all side effects of character's try to step
     * and returns if step should be done
     */
    abstract fun onStep(character: Character): StepResult
}