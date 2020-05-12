package ru.spbau.roguelike.net.grpc

import com.google.protobuf.Empty
import kotlinx.coroutines.flow.Flow
import ru.spbau.roguelike.net.server.Server

class RoguelikeGRPC(private val server: Server) : ServerConnectionGrpcKt.ServerConnectionCoroutineImplBase() {
    override fun game(requests: Flow<Request>): Flow<FieldInfoOrYourTurn> {
        TODO()
    }

    override suspend fun getActiveGames(request: Empty): ActiveGamesList {
        return ActiveGamesList.newBuilder()
            .addAllActiveGames(
                server.getActiveGames().map {
                    ActiveGamesList.ActiveGame.newBuilder()
                        .setId(it.id)
                        .setName(it.name)
                        .build()
                }
            )
            .build()
    }
}