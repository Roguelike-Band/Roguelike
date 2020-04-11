package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.EmptyCell
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.Wall
import java.io.File
import java.util.ArrayDeque
import kotlin.random.Random

/**
 * Factory for creating fields.
 *
 * Allows to load field from file or generate random field
 */
object FieldGenerator {
    /** Generates random connected field with given parameters */
    fun generateField(parameters: FieldGenerationParameters): Field {
        val field = Array(parameters.height) {
            Array(parameters.width) {
                if (Random.nextInt(0, 100) < parameters.wallPercentage) {
                    Wall()
                } else {
                    EmptyCell()
                }
            }
        }
        connectField(field)
        return Field(field)
    }

    private fun connectField(field: Array<Array<FieldObject>>) {
        val colors = paintComponents(field)
        val centralCell = Coordinates(field.size / 2, field[0].size / 2)
        val seenColors = mutableSetOf<Int>()
        for (row in 0..field.lastIndex) {
            for (column in 0..field[0].lastIndex) {
                if (colors[row][column] != 0 && colors[row][column] !in seenColors) {
                    connectCells(field, centralCell, Coordinates(row, column))
                    seenColors.add(colors[row][column])
                }
            }
        }
    }

    private fun paintComponents(field: Array<Array<FieldObject>>): Array<IntArray> {
        val colors = Array(field.size) { IntArray(field[0].size) { 0 } }
        var currentComponentId = 0
        for (row in 0..field.lastIndex) {
            for (column in 0..field[0].lastIndex) {
                if (field[row][column].objectType == FieldObjectType.EMPTY_CELL && colors[row][column] == 0) {
                    paintConcreteComponent(Coordinates(row, column), colors, ++currentComponentId, field)
                }
            }
        }
        return colors
    }

    private fun paintConcreteComponent(coordinates: Coordinates,
                                       colors: Array<IntArray>,
                                       currentColor: Int,
                                       field: Array<Array<FieldObject>>) {
        val queue = ArrayDeque<Coordinates>()
        queue.add(coordinates)
        colors[coordinates.row][coordinates.column] = currentColor
        while (queue.isNotEmpty()) {
            val currentCoordinates = queue.pollFirst()
            for (stepDirectory in listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))) {
                val newCoordinates = Coordinates(currentCoordinates.row + stepDirectory.first,
                    currentCoordinates.column + stepDirectory.second)

                if (0 <= newCoordinates.row && newCoordinates.row < colors.size &&
                    0 <= newCoordinates.column && newCoordinates.column < colors[0].size) {
                    if (field[newCoordinates.row][newCoordinates.column].objectType == FieldObjectType.EMPTY_CELL &&
                            colors[newCoordinates.row][newCoordinates.column] == 0) {
                        colors[newCoordinates.row][newCoordinates.column] = currentColor
                        queue.add(newCoordinates)
                    }
                }
            }
        }
    }

    private fun connectCells(field: Array<Array<FieldObject>>, from: Coordinates, to: Coordinates) {
        var currentCoordinates = from
        while (currentCoordinates != to) {
            field[currentCoordinates.row][currentCoordinates.column] = EmptyCell()
            val changeColumnCoordinate = currentCoordinates.row == to.row ||
                    (currentCoordinates.column != to.column && Random.nextBoolean())
            currentCoordinates = if (changeColumnCoordinate) {
                Coordinates(currentCoordinates.row,
                    currentCoordinates.column + moveDelta(currentCoordinates.column, to.column))
            } else {
                Coordinates(currentCoordinates.row + moveDelta(currentCoordinates.row, to.row),
                    currentCoordinates.column)
            }
        }
    }

    private fun moveDelta(from: Int, to: Int): Int {
        return if (from > to) {
            -1
        } else {
            1
        }
    }

    /**
     * Loads field from file.
     *
     * @throws IllegalArgumentException if file contains some illegal characters
     */
    fun loadField(file: File): Field {
        file.bufferedReader().use { reader ->
            return Field(reader.readLines().map { s ->
                Array(s.length) {
                    when (s[it]) {
                        'W' -> Wall()
                        '.' -> EmptyCell()
                        else -> throw IllegalArgumentException("Illegal character: ${s[it]}")
                    }
                }
            }.toTypedArray())
        }
    }
}