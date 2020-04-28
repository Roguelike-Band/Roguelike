package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo

/**
 * Interface for strategies. Each character has a strategy that
 * is responsible for making new turns.
 */
interface Strategy {
    /** Generate next cell this character will go to */
    fun generateCommand(character: Character, fieldInfo: FieldInfo): Command
}