package ru.spbau.roguelike.net.client

import ru.spbau.roguelike.controller.Turn

interface GameConnection {
    fun sendTurn(turn: Turn)
}
