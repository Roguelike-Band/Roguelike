package ru.spbau.roguelike.net.client

import ru.spbau.roguelike.controller.ConsoleDisplayController
import ru.spbau.roguelike.controller.ConsoleReaderController

class OnlineClientLogic(
    private val consoleDisplayController: ConsoleDisplayController,
    private val consoleReaderController: ConsoleReaderController,
    private val gameConnection: GameConnection
) {

}