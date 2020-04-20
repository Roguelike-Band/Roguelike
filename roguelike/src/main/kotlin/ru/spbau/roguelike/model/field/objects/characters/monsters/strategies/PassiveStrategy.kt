package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy

class PassiveStrategy : Strategy {
    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        return fieldInfo.coordinates
    }
}