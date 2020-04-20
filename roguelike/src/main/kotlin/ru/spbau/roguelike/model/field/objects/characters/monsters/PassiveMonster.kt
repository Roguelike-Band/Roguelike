package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.PassiveStrategy

class PassiveMonster : AbstractMonster(PassiveStrategy()) {
    override val objectType = FieldObjectType.PASSIVE_MONSTER
}