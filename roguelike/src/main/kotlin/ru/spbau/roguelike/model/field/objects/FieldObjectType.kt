package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Serializable

/** All types of objects on a field */
@Serializable
enum class FieldObjectType {
    EMPTY_CELL,
    INVISIBLE_CELL,
    WALL,
    PLAYER,
    DEFAULT_MONSTER,
    SHIELD
}