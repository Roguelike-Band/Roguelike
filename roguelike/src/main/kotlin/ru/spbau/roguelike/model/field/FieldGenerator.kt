package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.EmptyCell
import ru.spbau.roguelike.model.field.objects.Wall
import java.io.File
import kotlin.random.Random

/**
 * Factory for creating fields.
 *
 * Allows to load field from file or generate random field
 */
object FieldGenerator {
    /** Generates random field with given parameters */
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
        return Field(field)
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