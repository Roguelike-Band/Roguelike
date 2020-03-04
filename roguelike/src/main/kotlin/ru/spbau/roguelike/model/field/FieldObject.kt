package ru.spbau.roguelike.model.field

import ru.spbau.roguelike.model.field.objects.characters.Character

abstract class FieldObject {
    abstract val symbolOnBoard: Char

    abstract fun onStep(character: Character)
}