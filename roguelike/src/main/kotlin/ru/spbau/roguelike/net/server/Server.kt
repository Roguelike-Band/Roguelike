package ru.spbau.roguelike.net.server

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class Server {

    private val activeGamesMutex = Mutex()
    private val activeGames = mutableMapOf<Int, ActiveGame>()

    suspend fun getActiveGames(): List<ActiveGame> {
        activeGamesMutex.withLock {
            return activeGames.values.toList()
        }
    }
}