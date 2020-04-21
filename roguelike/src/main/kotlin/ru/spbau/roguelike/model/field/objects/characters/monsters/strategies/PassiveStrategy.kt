package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy


/** This strategy will always stand still. */
class PassiveStrategy : Strategy {
    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        return fieldInfo.coordinates
    }
}