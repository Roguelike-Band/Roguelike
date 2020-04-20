package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import kotlin.random.Random

class ConfusedStrategy(
        private val previousStrategy: Strategy,
        private var confuseTime: Int,
        private val character: Character
) : Strategy {
    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        --confuseTime
        if (confuseTime == 0) {
            character.unconfuse(previousStrategy)
        }

        val currentCoordinates = fieldInfo.coordinates
        val rowChange = Random.nextBoolean()
        val coordinateChange = listOf(-1, 1).random()
        return if (rowChange) {
            Coordinates(currentCoordinates.row + coordinateChange, currentCoordinates.column)
        } else {
            Coordinates(currentCoordinates.row, currentCoordinates.column + coordinateChange)
        }
    }
}
