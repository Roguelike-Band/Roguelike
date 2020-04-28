package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.*


/** This strategy will always stand still. */
@Serializable
class PassiveStrategy : Strategy {
    override fun generateCommand(character: Character, fieldInfo: FieldInfo): Command {
        return MoveToCoordinatesCommand(fieldInfo.coordinates)
    }
}