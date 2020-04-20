package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.AggressiveStrategy

class AggressiveMonster : AbstractMonster(AggressiveStrategy()) {
    override val objectType = FieldObjectType.AGGRESSIVE_MONSTER
}