package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.FieldObjectType

class DisplayFieldInfo(private val field: Array<Array<FieldObjectType>>, val coordinates: Coordinates) {
    val height = field.size
    val width = field[0].size

    operator fun get(coordinates: Coordinates): FieldObjectType {
        return field[coordinates.row][coordinates.column]
    }
}
