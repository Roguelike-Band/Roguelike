package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.model.field.Field

internal data class GameState(
    val field: Field,
    val characters: MutableList<CharacterInfo> = mutableListOf(),
    val logicHelper: LogicHelper = LogicHelper(field, characters)
)