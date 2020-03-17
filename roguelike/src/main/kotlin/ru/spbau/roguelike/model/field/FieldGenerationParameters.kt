package ru.spbau.roguelike.model.field

import java.lang.IllegalArgumentException

/** Parameters for random field generation */
class FieldGenerationParameters(
    val height: Int,
    val width: Int,
    /**
     * Number from 0 to 100.
     *
     * Possibility of wall to be generated in each cell in percents
     */
    val wallPercentage: Int
) {
    init {
        if (height <= 0 || width <= 0) {
            throw IllegalArgumentException("Height and width of the field must be positive")
        }
        if (wallPercentage < 0 || wallPercentage > 100) {
            throw IllegalArgumentException("Wall percentage of the field must be in range [0; 100]")
        }
    }
}