package ru.spbau.roguelike.controller.turncommands

import ru.spbau.roguelike.controller.Turn

/** Command typed by user */
sealed class UserCommand(val commandType: Turn)

/** Move command typed by user */
class MoveUserCommand(turn: Turn) : UserCommand(turn)

/** Equipment command (put on/take off) typed by user */
class EquipmentUserCommand(turn: Turn, val index: Int) : UserCommand(turn)