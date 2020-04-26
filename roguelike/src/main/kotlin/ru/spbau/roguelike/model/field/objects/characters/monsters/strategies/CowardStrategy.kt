package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy

/**
 * If player isn't visible or player can't reach monster, this strategy will stand still.
 * Otherwise, it will make a turn that makes him as far from player as possible.
 */
@Serializable
class CowardStrategy : Strategy {

    private fun generateStepWithNoVisiblePlayer(fieldInfo: FieldInfo): Coordinates {
        return fieldInfo.coordinates
    }

    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        return BFS(fieldInfo).getFartherFromPlayer() ?: generateStepWithNoVisiblePlayer(fieldInfo)
    }
}