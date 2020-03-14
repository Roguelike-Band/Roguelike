package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Monster

class DefaultMonster(fieldInfo: FieldInfo) : Monster(fieldInfo) {
    override val objectType = FieldObjectType.DEFAULT_MONSTER

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    override fun doTurn() {
        TODO("Not implemented")
    }
}