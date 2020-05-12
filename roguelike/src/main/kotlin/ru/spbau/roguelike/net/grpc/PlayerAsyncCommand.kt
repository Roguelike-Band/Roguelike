package ru.spbau.roguelike.net.grpc

import kotlinx.coroutines.channels.Channel
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.CharacterView

sealed class PlayerAsyncCommand

class ReadTurnCommand : PlayerAsyncCommand() {
    val channel = Channel<Command>()
}

object CloseConnection : PlayerAsyncCommand()

class NewUserCommand(val command: Command) : PlayerAsyncCommand()

class SendField(val fieldInfo: DisplayFieldInfo, val playerView: CharacterView) : PlayerAsyncCommand()


