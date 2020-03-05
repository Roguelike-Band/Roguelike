package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.InvisibleCell

class FieldInfo(
    private val field: Field,
    private val isCellVisible: Array<BooleanArray>
) {

    operator fun get(xCoordinate: Int, yCoordinate: Int): FieldObject {
        if (!isCellVisible[xCoordinate][yCoordinate]) {
            return InvisibleCell
        }
        return field[xCoordinate, yCoordinate]
    }

    fun makeCellVisible(xCoordinate: Int, yCoordinate: Int) {
        isCellVisible[xCoordinate][yCoordinate] = true
    }

    operator fun set(xCoordinate: Int, yCoordinate: Int, newObject: FieldObject) {
        field[xCoordinate, yCoordinate] = newObject
    }
}