package ru.spbau.roguelike.model.field.objects

/** All types of objects on a field */
enum class FieldObjectType {
    EMPTY_CELL,
    INVISIBLE_CELL,
    WALL,
    PLAYER,
    AGGRESSIVE_MONSTER,
    COWARD_MONSTER,
    PASSIVE_MONSTER,
    SHIELD,
    USELESS_EQUIPMENT,
    RANDOM_EQUIPMENT,
    ONLINE_PLAYER
}
