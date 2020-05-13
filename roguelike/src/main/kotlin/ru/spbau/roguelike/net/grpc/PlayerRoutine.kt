package ru.spbau.roguelike.net.grpc

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.CharacterView
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.net.server.Server

@ExperimentalCoroutinesApi
class PlayerRoutine(private val server: Server) {
    private val channel = Channel<PlayerAsyncCommand>()

    fun start(): Flow<FieldInfoOrYourTurn> = flow {
        var awaitingTurnChannel: Channel<Command>? = null

        loop@while (true) {
            when (val received = channel.receive()) {

                is ReadTurnCommand -> {
                    emit(
                        FieldInfoOrYourTurn.newBuilder()
                            .setYourTurn(
                                YourTurn.newBuilder()
                                    .build()
                            )
                            .build()
                    )
                    awaitingTurnChannel = received.channel
                }

                is NewUserCommand -> {
                    awaitingTurnChannel?.send(received.command)
                    awaitingTurnChannel = null
                }

                is SendField -> {
                    val convertedMessage = ProtobufConverter.convertToFieldInfoMessage(
                        received.playerView,
                        received.fieldInfo
                    )
                    emit(
                        FieldInfoOrYourTurn.newBuilder()
                            .setFieldInfo(convertedMessage)
                            .build()
                    )
                }

                is CreateNewGame -> {
                    TODO()
                }

                is ConnectToGame -> {
                    TODO()
                }

                is CloseConnection -> {
                    // TODO notify server
                    break@loop
                }
            }
        }
        channel.close()
        awaitingTurnChannel?.close()
    }

    suspend fun asyncCloseConnection() {
        channel.send(CloseConnection)
    }

    suspend fun asyncReceiveUserCommand(command: Command) {
        channel.send(NewUserCommand(command))
    }

    suspend fun asyncSendField(fieldInfo: DisplayFieldInfo, character: CharacterView) {
        channel.send(SendField(fieldInfo, character))
    }

    suspend fun asyncCreateNewGame(name: String) {
        channel.send(CreateNewGame(name))
    }

    suspend fun asyncConnectToGame(gameId: Int) {
        channel.send(ConnectToGame(gameId))
    }

    fun readUserTurn(): Command {
        val command = ReadTurnCommand()
        return runBlocking {
            channel.send(command)
            command.channel.receive()
        }
    }
}
