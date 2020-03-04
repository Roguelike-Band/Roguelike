package ru.spbau.roguelike.model.field.objects.characters

class Player : Character() {
    override val symbolOnBoard: Char = '@'

    override fun onStep(character: Character) {
        TODO("not implemented")
    }
}