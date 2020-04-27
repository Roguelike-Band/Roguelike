package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
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

    override fun generateStep(character: Character, fieldInfo: FieldInfo): Command {
        return MoveCommand(BFS(fieldInfo).getFartherFromPlayer() ?: generateStepWithNoVisiblePlayer(fieldInfo))
    }
}