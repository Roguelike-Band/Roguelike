package ru.spbau.roguelike.model.logic

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.characters.player.Player

/** All information about game that logic needs */
@Serializable
class GameInfo(val fieldInfo: FieldInfo, val player: Player, val movementExecutor: MovementExecutor)