package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.FieldObject

/** Grid with all information about game */
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

    /** Checks if given coordinates are coordinates of some cell in a field */
    fun isGood(coordinates: Coordinates): Boolean {
        return coordinates.row in (0 until height) &&
                coordinates.column in (0 until width)
    }

    /**
     * Moves object that was on `oldCoordinates` to `newCoordinates`.
     *
     * Places empty cell to `oldCoordinates`
     */
    fun move(oldCoordinates: Coordinates, newCoordinates: Coordinates) {
        val movingObject = get(oldCoordinates)
        set(oldCoordinates, EmptyCell())
        set(newCoordinates, movingObject)
    }
}