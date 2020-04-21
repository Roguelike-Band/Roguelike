package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.CowardStrategy

/** See [CowardStrategy] for description of this monster behaviour. */
class CowardMonster : AbstractMonster(CowardStrategy(), Attributes(COWARD_HEALTH, COWARD_MAX_POWER)) {
    override val objectType = FieldObjectType.COWARD_MONSTER

    private companion object {
        const val COWARD_HEALTH = 1
        const val COWARD_MAX_POWER = 2
    }
}