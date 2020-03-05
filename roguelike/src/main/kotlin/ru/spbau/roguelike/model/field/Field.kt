package ru.spbau.roguelike.model.field

class Field(
    private val field: Array<Array<FieldObject>>
) {
    val height = field.size
    val width = field[0].size

    operator fun get(xCoordinate: Int, yCoordinate: Int): FieldObject {
        return field[xCoordinate][yCoordinate]
    }

    operator fun set(xCoordinate: Int, yCoordinate: Int, newObject: FieldObject) {
        field[xCoordinate][yCoordinate] = newObject
    }
}