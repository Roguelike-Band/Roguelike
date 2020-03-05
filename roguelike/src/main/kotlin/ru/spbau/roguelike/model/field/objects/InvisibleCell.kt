package ru.spbau.roguelike.model.field.objects

import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.objects.characters.Character

object InvisibleCell : FieldObject() {
    override val symbolOnBoard: Char = ' '

    override fun onStep(character: Character) {
        TODO("not implemented")
    }
}