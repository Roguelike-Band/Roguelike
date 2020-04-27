package ru.spbau.roguelike.model.logic

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Logic of epoch of turns */
@Serializable
class TurnLogic(
        private val character: Character,
        private val fieldInfo: FieldInfo,
        private val movementExecutor: MovementExecutor
) {

    /** Asks all characters to do their turn */
    fun doTurn() {
        if (character.isAlive) {
            character.doTurn(fieldInfo, movementExecutor)
        }
    }
}
