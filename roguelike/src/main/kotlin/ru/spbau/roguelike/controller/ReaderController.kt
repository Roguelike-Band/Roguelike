package ru.spbau.roguelike.controller

/** Interface for model getting turns from user */
interface ReaderController {
    /** Returns user's new turn */
    fun readTurn(): Turn
}