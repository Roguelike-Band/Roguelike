package ru.spbau.roguelike.model.field.objects.characters.monsters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.AggressiveStrategy

/** See [AggressiveStrategy] for description of this monster behaviour. */
@Serializable
class AggressiveMonster : AbstractMonster(AggressiveStrategy(), Attributes(AGGRESSIVE_HEALTH, AGGRESSIVE_MAX_POWER)) {
    override val objectType = FieldObjectType.AGGRESSIVE_MONSTER

    companion object {
        private const val AGGRESSIVE_HEALTH = 5
        private const val AGGRESSIVE_MAX_POWER = 10
    }
}