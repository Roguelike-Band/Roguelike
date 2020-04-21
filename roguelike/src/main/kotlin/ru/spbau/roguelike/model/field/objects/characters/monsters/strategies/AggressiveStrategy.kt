package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy

class AggressiveStrategy : Strategy {

    private fun generateStepWithNoVisiblePlayer(fieldInfo: FieldInfo): Coordinates {
        return fieldInfo.coordinates
    }

    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        return BFS(fieldInfo).getCloserToPlayer() ?: generateStepWithNoVisiblePlayer(fieldInfo)
    }
}