package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Serializable

@Serializable
enum class FieldObjectType {
    EMPTY_CELL,
    INVISIBLE_CELL,
    WALL,
    PLAYER,
    DEFAULT_MONSTER,
    SHIELD
}