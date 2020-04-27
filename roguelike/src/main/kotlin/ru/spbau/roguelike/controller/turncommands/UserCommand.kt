package ru.spbau.roguelike.controller.turncommands

import ru.spbau.roguelike.controller.Turn

sealed class UserCommand(val commandType: Turn)

class MoveUserCommand(turn: Turn) : UserCommand(turn)

class EquipmentUserCommand(turn: Turn, val index: Int) : UserCommand(turn)