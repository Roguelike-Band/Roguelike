package ru.spbau.roguelike.net.grpc

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class PlayerRoutine {
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
                    TODO()
                }

                is CloseConnection -> {
                    break@loop
                }
            }
        }
        channel.close()
    }


}