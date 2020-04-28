package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import kotlin.random.Random

/**
 * Temporary strategy for any character that became confused
 * as a result of enemy's attack.
 * Confused characters make a random turn for [confuseTime] turns,
 * then return to [previousStrategy].
 */
@Serializable
class ConfusedStrategy(
        private val previousStrategy: Strategy,
        private var confuseTime: Int
) : Strategy {
    override fun generateCommand(character: Character, fieldInfo: FieldInfo): Command {
        --confuseTime
        if (confuseTime == 0) {
            character.unconfuse(previousStrategy)
        }

        val currentCoordinates = fieldInfo.coordinates
        val rowChange = Random.nextBoolean()
        val coordinateChange = listOf(-1, 1).random()
        return MoveCommand(
            if (rowChange) {
                Coordinates(currentCoordinates.row + coordinateChange, currentCoordinates.column)
            } else {
                Coordinates(currentCoordinates.row, currentCoordinates.column + coordinateChange)
            }
        )
    }
}
