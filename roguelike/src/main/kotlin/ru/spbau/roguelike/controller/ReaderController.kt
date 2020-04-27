package ru.spbau.roguelike.controller

import ru.spbau.roguelike.controller.turncommands.UserCommand

/** Interface for model getting turns from user */
interface ReaderController {
    /** Returns user's new turn */
    fun readTurn(): UserCommand
}