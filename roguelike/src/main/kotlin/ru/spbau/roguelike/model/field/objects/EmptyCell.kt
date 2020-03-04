package ru.spbau.roguelike.model.field.objects

import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.objects.characters.Character

class EmptyCell : FieldObject() {
    override val symbolOnBoard: Char = ' '

    override fun onStep(character: Character) {
        TODO("Not implemented")
    }
}