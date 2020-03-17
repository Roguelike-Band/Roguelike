package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.InvisibleCell
import ru.spbau.roguelike.model.field.objects.characters.Player

/** Information about field that is known to character */
@Serializable
class FieldInfo(
    private val field: Field,
    @Suppress("CanBeParameter") private val currentCoordinates: Coordinates
) {
    val height = field.height
    val width = field.width
    private val isCellVisible = Array(height) { BooleanArray(width) { false } }
    var coordinates = currentCoordinates
        private set

    /** Returns field object on this cell and `InvisibleCell` if cell is invisible to character*/
    operator fun get(coordinates: Coordinates): FieldObject {
        if (!isCellVisible[coordinates.row][coordinates.column]) {
            return InvisibleCell()
        }
        return field[coordinates]
    }

    private fun makeCellVisible(coordinates: Coordinates) {
        isCellVisible[coordinates.row][coordinates.column] = true
    }

    operator fun set(coordinates: Coordinates, newObject: FieldObject) {
        field[coordinates] = newObject
    }

    /** Changes characters position */
    fun moveTo(newCoordinates: Coordinates) {
        field.move(coordinates, newCoordinates)
        coordinates = newCoordinates
    }

    /** Checks if given coordinates are coordinates of some cell in a field */
    fun isGood(coordinates: Coordinates): Boolean {
        return coordinates.row in (0 until field.height) &&
                coordinates.column in (0 until field.width)
    }

    /** Changes character's visible part of field */
    fun setVisibleNeighbourhood(vision: Int) {
        for (rowDiff in -vision..vision) {
            for (columnDiff in -vision..vision) {
                val row = rowDiff + coordinates.row
                val column = columnDiff + coordinates.column
                val coordinates = Coordinates(row, column)
                if (isGood(coordinates)) {
                    makeCellVisible(coordinates)
                }
            }
        }
    }
}