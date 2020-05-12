package ru.spbau.roguelike.net.client

interface BeforeGameConnection {
    fun connectToGame(game: ActiveGame): GameConnection
    fun createNewGame(name: String): GameConnection
    fun getActiveGames(): List<ActiveGame>
}
