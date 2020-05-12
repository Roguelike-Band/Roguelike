package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.cells.InvisibleCell

/** Information about field that is known to character */
class FieldInfo(
    val field: Field,
    private var currentCoordinates: Coordinates,
    startingCellVisible: Array<BooleanArray>? = null
) {
    val height = field.height
    val width = field.width
    val isCellVisible = startingCellVisible ?: Array(height) { BooleanArray(width) { false } }
    var coordinates: Coordinates
        get() = currentCoordinates
        private set(value) {
            currentCoordinates = value
        }

    /** Returns field object on this cell and `InvisibleCell` if cell is invisible to character*/
    operator fun get(coordinates: Coordinates): FieldObject {
        if (!isCellVisible[coordinates.row][coordinates.column]) {
            return InvisibleCell()
        }
        return field[coordinates]
    }

    /** Checks if given coordinates are coordinates of some cell in a field */
    fun isGood(coordinates: Coordinates): Boolean {
        return field.isGood(coordinates)
    }

    private fun makeCellVisible(coordinates: Coordinates) {
        isCellVisible[coordinates.row][coordinates.column] = true
    }

    operator fun set(coordinates: Coordinates, newObject: FieldObject) {
        field[coordinates] = newObject
    }

    /**
     * Changes characters position.
     *
     * Does not initiates side effects. Just moves character. Should be called only after
     * all side effects
     */
    fun moveTo(newCoordinates: Coordinates) {
        field.move(currentCoordinates, newCoordinates)
        currentCoordinates = newCoordinates
    }

    /** Changes character's visible part of field */
    fun setVisibleNeighbourhood(vision: Int) {
        for (rowDiff in -vision..vision) {
            for (columnDiff in -vision..vision) {
                val row = rowDiff + coordinates.row
                val column = columnDiff + coordinates.column
                val coordinates = Coordinates(row, column)
                if (field.isGood(coordinates)) {
                    makeCellVisible(coordinates)
                }
            }
        }
    }

    fun toDisplayFieldInfo(): DisplayFieldInfo {
        val displayArray = Array<Array<FieldObjectType>>(height) { row ->
            Array(width) { column ->
                this[Coordinates(row, column)].objectType
            }
        }
        return DisplayFieldInfo(displayArray, currentCoordinates)
    }
}
