package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.InvisibleCell
import ru.spbau.roguelike.model.field.objects.characters.Player

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

    operator fun get(coordinates: Coordinates): FieldObject {
        if (!isCellVisible[coordinates.row][coordinates.column]) {
            return InvisibleCell()
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

    fun setVisibleNeighbourhood() {
        for (rowDiff in -Player.PLAYER_START_VISION..Player.PLAYER_START_VISION) {
            for (columnDiff in -Player.PLAYER_START_VISION..Player.PLAYER_START_VISION) {
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