package ru.spbau.roguelike.model.field.objects.characters.player

import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character

/** User's character */
class Player(readerController: ReaderController) : Character(PlayerStrategy(readerController),
        Attributes(PLAYER_HEALTH, PLAYER_MAX_POWER)) {

    private companion object {
        const val PLAYER_HEALTH = 50
        const val PLAYER_MAX_POWER = 10
    }

    override val vision = 5

    override val objectType = FieldObjectType.PLAYER
}
