package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.InvisibleCell

class FieldInfo(
    private val field: Field,
    currentCoordinates: Coordinates
) {
    val height = field.height
    val width = field.width
    private val isCellVisible = Array(height) { BooleanArray(width) { false } }
    var coordinates = currentCoordinates
        private set

    operator fun get(coordinates: Coordinates): FieldObject {
        if (!isCellVisible[coordinates.row][coordinates.column]) {
            return InvisibleCell
        }
        return field[coordinates]
    }

    fun makeCellVisible(coordinates: Coordinates) {
        isCellVisible[coordinates.row][coordinates.column] = true
    }

    operator fun set(coordinates: Coordinates, newObject: FieldObject) {
        field[coordinates] = newObject
    }

    fun moveTo(newCoordinates: Coordinates) {
        field.move(coordinates, newCoordinates)
        coordinates = newCoordinates
    }

    fun isGood(coordinates: Coordinates): Boolean {
        return coordinates.row in (0 until field.height) &&
                coordinates.column in (0 until field.width)
    }
}