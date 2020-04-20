package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.characters.Character

/** Logic of epoch of turns */
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
