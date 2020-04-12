package ru.spbau.roguelike.model.field.objects.characters.monsters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Monster

@Serializable
class DefaultMonster : Monster() {
    companion object {
        const val DEFAULT_MONSTER_VISION = 5
    }

    override val objectType = FieldObjectType.DEFAULT_MONSTER

    override val vision: Int = DEFAULT_MONSTER_VISION

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        @Suppress("UNREACHABLE_CODE")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    override fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        TODO("Not implemented")
    }
}