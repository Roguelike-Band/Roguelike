package ru.spbau.roguelike.net.grpc

import com.google.protobuf.Empty
import kotlinx.coroutines.flow.Flow

class RoguelikeGRPC : ServerConnectionGrpcKt.ServerConnectionCoroutineImplBase() {
    override suspend fun connectToGame(request: ActiveGameId): Empty {
        TODO()
    }

    override suspend fun createNewGame(request: GameName): Empty {
        TODO()
    }

    override fun game(requests: Flow<Command>): Flow<FieldInfoOrYourTurn> {
        TODO()
    }

    override suspend fun getActiveGames(request: Empty): ActiveGamesList {
        TODO()
    }
}