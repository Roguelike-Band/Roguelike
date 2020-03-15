package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.EmptyCell

@Serializable
class Field(
    private val field: Array<Array<FieldObject>>
) {
    val height = field.size
    val width = field[0].size

    operator fun get(coordinates: Coordinates): FieldObject {
        return field[coordinates.row][coordinates.column]
    }

    operator fun set(coordinates: Coordinates, newObject: FieldObject) {
        field[coordinates.row][coordinates.column] = newObject
    }

    fun move(oldCoordinates: Coordinates, newCoordinates: Coordinates) {
        val movingObject = get(oldCoordinates)
        set(oldCoordinates, EmptyCell())
        set(newCoordinates, movingObject)
    }
}