package ru.spbau.roguelike.model.field.objects.characters.player

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character

/** User's character */
@Serializable
class Player(
    private val readerController: ReaderController,
    private val displayController: DisplayController
) : Character(PlayerStrategy(readerController),
        Attributes(PLAYER_HEALTH, PLAYER_MAX_POWER)) {

    companion object {
        private const val PLAYER_HEALTH = 50
        private const val PLAYER_MAX_POWER = 10
    }

    override val vision = 5

    override val objectType = FieldObjectType.PLAYER

    override fun refreshPlayerUI(fieldInfo: FieldInfo) {
        displayController.refreshGameField(fieldInfo.toDisplayFieldInfo(), toPlayerView())
    }

    fun toPlayerView(): PlayerView {
        return PlayerView(isAlive, attributes, equipmentList.toEquipmentListView())
    }
}
