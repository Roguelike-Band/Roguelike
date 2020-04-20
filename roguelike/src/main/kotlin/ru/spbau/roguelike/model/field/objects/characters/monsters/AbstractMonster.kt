package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Strategy

abstract class AbstractMonster(strategy: Strategy) : Character(strategy) {
    override val vision = 5

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        // TODO: Start battle
        @Suppress("UNREACHABLE_CODE")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }
}