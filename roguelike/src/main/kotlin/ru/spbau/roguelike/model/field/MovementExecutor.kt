package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.characters.Character

/** Class for processing characters' moves */
class MovementExecutor(private val field: Field) {
    fun executeMove(character: Character, to: Coordinates, charactersFieldInfo: FieldInfo) {
        @Suppress("ControlFlowWithEmptyBody")
        if (field.isGood(to) && to != charactersFieldInfo.coordinates) {
            val stepResult = field[to].onStep(character)
            if (stepResult == StepResult.STEP_SHOULD_BE_DONE) {
                charactersFieldInfo.moveTo(to)
            }
        } else {
            // Skip turn
        }
    }
}
