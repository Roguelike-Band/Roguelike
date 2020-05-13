package ru.spbau.roguelike.net.grpc

import com.google.protobuf.Empty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
import ru.spbau.roguelike.model.field.objects.characters.PutOnEquipmentCommand
import ru.spbau.roguelike.model.field.objects.characters.TakeOffEquipmentCommand
import ru.spbau.roguelike.net.server.Server

@ExperimentalCoroutinesApi
class RoguelikeGRPC(private val server: Server) : ServerConnectionGrpcKt.ServerConnectionCoroutineImplBase() {

    override fun game(requests: Flow<Request>): Flow<FieldInfoOrYourTurn> {

        val player = PlayerRoutine(server)

        val collector = GameRequestsCollector(player)

        GlobalScope.launch {
            collector.emitAll(requests)
        }

        return player.start()
    }

    override suspend fun getActiveGames(request: Empty): ActiveGamesList {
        return ActiveGamesList.newBuilder()
            .addAllActiveGames(
                server.getActiveGames().map {
                    ActiveGame.newBuilder()
                        .setId(it.id)
                        .setName(it.name)
                        .build()
                }
            )
            .build()
    }
    
    private class GameRequestsCollector(private val playerRoutine: PlayerRoutine) : FlowCollector<Request> {
        private var isFirstValue = false
        private var gameId: Int = 0
        private var playerId = 0

        /**
         * Collects the value emitted by the upstream.
         * This method is not thread-safe and should not be invoked concurrently.
         */
        override suspend fun emit(value: Request) {
            if (isFirstValue) {
                emitInitValue(value)
            } else {
                emitGameValue(value)
            }
        }

        private suspend fun emitInitValue(value: Request) {
            when {
                value.hasConnectToActiveGame() -> {
                    val connect = value.connectToActiveGame!!
                    val gameId = connect.gameId
                    playerRoutine.asyncConnectToGame(gameId)
                }
                value.hasCreateNewGame() -> {
                    val connect = value.createNewGame!!
                    val gameName = connect.gameName
                    playerRoutine.asyncCreateNewGame(gameName)
                }
                else -> {
                    playerRoutine.asyncCloseConnection()
                }
            }
            isFirstValue = false
        }

        private suspend fun emitGameValue(value: Request) = when {
            value.hasCommand() -> {
                val gameCommand = ProtobufConverter.convertCommand(value.command!!)
                playerRoutine.asyncReceiveUserCommand(gameCommand)
            }
            else -> {
                playerRoutine.asyncCloseConnection()
            }
        }
    }
}