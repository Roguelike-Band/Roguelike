package ru.spbau.roguelike.net.grpc

import kotlinx.coroutines.channels.Channel
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.CharacterView
import ru.spbau.roguelike.model.field.objects.characters.Command

sealed class PlayerAsyncCommand

/** Asynchronous command for sending user [YourTurn]  and receiving user's turn into [channel] */
class ReadTurnCommand : PlayerAsyncCommand() {
    val channel = Channel<Command>()
}

object CloseConnection : PlayerAsyncCommand()

/** Command that was send by user and received by us */
class NewUserCommand(val command: Command) : PlayerAsyncCommand()

class SendField(val fieldInfo: DisplayFieldInfo, val playerView: CharacterView) : PlayerAsyncCommand()

class ConnectToGame(val gameId: Int) : PlayerAsyncCommand()

class CreateNewGame(val gameName: String) : PlayerAsyncCommand()
