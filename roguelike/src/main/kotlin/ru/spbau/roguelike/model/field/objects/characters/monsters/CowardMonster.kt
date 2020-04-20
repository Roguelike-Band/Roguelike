package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.CowardStrategy

class CowardMonster : AbstractMonster(CowardStrategy()) {
    override val objectType = FieldObjectType.COWARD_MONSTER
}