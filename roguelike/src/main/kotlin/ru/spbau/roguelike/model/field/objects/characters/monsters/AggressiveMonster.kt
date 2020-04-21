package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.AggressiveStrategy

/** See [AggressiveStrategy] for description of this monster behaviour. */
class AggressiveMonster : AbstractMonster(AggressiveStrategy(), Attributes(AGGRESSIVE_HEALTH, AGGRESSIVE_MAX_POWER)) {
    override val objectType = FieldObjectType.AGGRESSIVE_MONSTER

    private companion object {
        const val AGGRESSIVE_HEALTH = 5
        const val AGGRESSIVE_MAX_POWER = 10
    }
}