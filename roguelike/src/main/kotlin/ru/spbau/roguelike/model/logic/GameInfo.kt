package ru.spbau.roguelike.model.logic

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Player

@Serializable
class GameInfo(val fieldInfo: FieldInfo, val player: Player)