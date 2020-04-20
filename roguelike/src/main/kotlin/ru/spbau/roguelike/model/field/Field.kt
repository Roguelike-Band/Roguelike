package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.FieldObject
import kotlin.random.Random

/** Grid with all information about game */
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

    fun getRandomEmptyCell(): Coordinates {
        val possibleCoordinates = mutableListOf<Coordinates>()
        for (row in 0 until height) {
            for (column in 0 until width) {
                val coordinates = Coordinates(row, column)
                if (get(coordinates).canBeCharacterStartCell) {
                    possibleCoordinates.add(coordinates)
                }
            }
        }

        return possibleCoordinates[Random.nextInt(possibleCoordinates.size)]
    }
}