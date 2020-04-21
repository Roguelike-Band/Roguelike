package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.PassiveStrategy

/** See [PassiveStrategy] for description of this monster behaviour. */
class PassiveMonster : AbstractMonster(PassiveStrategy(), Attributes(PASSIVE_HEALTH, PASSIVE_MAX_POWER)) {
    override val objectType = FieldObjectType.PASSIVE_MONSTER

    private companion object {
        const val PASSIVE_HEALTH = 4
        const val PASSIVE_MAX_POWER = 4
    }
}