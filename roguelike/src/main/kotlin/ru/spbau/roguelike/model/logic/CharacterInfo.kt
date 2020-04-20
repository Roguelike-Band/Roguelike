package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.characters.Character

/** All information about game that logic needs */
data class CharacterInfo(
        val isRealCharacter: Boolean,
        val fieldInfo: FieldInfo,
        val character: Character,
        val movementExecutor: MovementExecutor,
        val turnLogic: TurnLogic,
        val afterTurnLogic: AfterTurnLogic
)