package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.EmptyCell
import ru.spbau.roguelike.model.field.objects.Wall
import kotlin.random.Random

object FieldGenerator {
    fun generateField(parameters: FieldGenerationParameters): Field {
        val field = Array(parameters.height) {
            Array(parameters.width) {
                if (Random.nextInt(0, 100) < parameters.wallPercentage) {
                    Wall
                } else {
                    EmptyCell
                }
            }
        }
        return Field(field)
    }
}