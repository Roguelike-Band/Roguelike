package ru.spbau.roguelike.model.field.objects.characters.player

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.*

/**
 * Strategy for player's turns.
 * Waits for input and then moves as instructed by player.
 */
@Serializable
class PlayerStrategy(private val readerController: ReaderController) : Strategy {
    override fun generateCommand(character: Character, fieldInfo: FieldInfo): Command {
        return readerController.readCommand(fieldInfo)
    }
}