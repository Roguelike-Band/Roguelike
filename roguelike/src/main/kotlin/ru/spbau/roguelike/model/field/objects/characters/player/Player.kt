package ru.spbau.roguelike.model.field.objects.characters.player

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

/** User's character */
@Serializable
class Player(private val readerController: ReaderController) : Character(PlayerStrategy(readerController)) {
    companion object {
        const val PLAYER_START_VISION = 5
    }

    override val vision: Int = PLAYER_START_VISION

    override val objectType = FieldObjectType.PLAYER

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
        @Suppress("UNREACHABLE_CODE", "ControlFlowWithEmptyBody")
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }
}
