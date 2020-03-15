package ru.spbau.roguelike.model.field.objects.characters.monsters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Monster

@Serializable
class DefaultMonster : Monster() {
    override val objectType = FieldObjectType.DEFAULT_MONSTER

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    override fun doTurn(fieldInfo: FieldInfo) {
        TODO("Not implemented")
    }
}