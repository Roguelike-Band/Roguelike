package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
import ru.spbau.roguelike.model.field.objects.characters.Strategy


/** This strategy will always stand still. */
@Serializable
class PassiveStrategy : Strategy {
    override fun generateStep(character: Character, fieldInfo: FieldInfo): Command {
        return MoveCommand(fieldInfo.coordinates)
    }
}