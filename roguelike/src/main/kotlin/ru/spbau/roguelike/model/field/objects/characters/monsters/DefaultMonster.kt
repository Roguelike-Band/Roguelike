package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Monster

class DefaultMonster : Monster() {
    override val symbolOnBoard: Char = 'a'

    override fun onStep(character: Character) {
        TODO("not implemented")
    }

    override fun doTurn() {
        TODO("Not implemented")
    }
}