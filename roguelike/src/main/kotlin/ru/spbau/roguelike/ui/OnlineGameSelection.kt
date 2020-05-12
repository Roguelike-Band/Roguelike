package ru.spbau.roguelike.ui

import ru.spbau.roguelike.net.client.ActiveGame
import ru.spbau.roguelike.net.client.BeforeGameConnection
import ru.spbau.roguelike.net.client.GRPCConnectionBuilder

class OnlineGameSelection(private val lanterna: Lanterna) {

    private lateinit var beforeGameConnection: BeforeGameConnection

    fun start() {
        lanterna.askForIpAndPort(this)
    }

    fun onIpAndPortWritten(host: String, port: Int) {
        beforeGameConnection = GRPCConnectionBuilder.connect(host, port)
        val activeGames = beforeGameConnection.getActiveGames()
        lanterna.showActiveGames(activeGames, this)
    }

    fun onGameSelected(game: ActiveGame) {
        val gameConnection = beforeGameConnection.connectToGame(game)
        OnlineGameStarter(lanterna, gameConnection).start()
    }

    fun onCreateNewGame(name: String) {
        val gameConnection = beforeGameConnection.createNewGame(name)
        OnlineGameStarter(lanterna, gameConnection).start()
    }
}
