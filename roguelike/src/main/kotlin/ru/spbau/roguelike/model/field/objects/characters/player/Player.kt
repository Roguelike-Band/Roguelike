package ru.spbau.roguelike.model.field.objects.characters.player

import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character

/** User's character */
class Player(readerController: ReaderController) : Character(PlayerStrategy(readerController),
        Attributes(PLAYER_HEALTH, PLAYER_MAX_POWER)) {

    companion object {
        const val PLAYER_START_VISION = 5

        const val PLAYER_HEALTH = 100
        const val PLAYER_MAX_POWER = 10
    }

    override val vision: Int = PLAYER_START_VISION

    override val objectType = FieldObjectType.PLAYER
}
