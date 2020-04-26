package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Strategy

/**
 * If player isn't visible or monster can't reach player, this strategy will stand still.
 * Otherwise, it will make a turn into player's direction and attack him
 * if he's adjacent.
 */
@Serializable
class AggressiveStrategy : Strategy {

    private fun generateStepWithNoVisiblePlayer(fieldInfo: FieldInfo): Coordinates {
        return fieldInfo.coordinates
    }

    override fun generateStep(fieldInfo: FieldInfo): Coordinates {
        return BFS(fieldInfo).getCloserToPlayer() ?: generateStepWithNoVisiblePlayer(fieldInfo)
    }
}