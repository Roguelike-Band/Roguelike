package ru.spbau.roguelike.model.logic

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeStructure
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerialModule
import kotlinx.serialization.modules.SerializersModule
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.InvisibleCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.ConfusedStrategy
import ru.spbau.roguelike.model.field.objects.characters.Strategy
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.AggressiveMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.CowardMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.PassiveMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.AggressiveStrategy
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.CowardStrategy
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.PassiveStrategy
import ru.spbau.roguelike.model.field.objects.characters.player.Player
import ru.spbau.roguelike.model.field.objects.equipment.Equipment
import java.io.File

class PlayerSerializer(private val readerController: ReaderController?, private val displayController: DisplayController?) : KSerializer<Player> {

    override fun serialize(encoder: Encoder, value: Player) {
        encoder.encodeStructure(descriptor) {

        }
    }

    override fun deserialize(decoder: Decoder): Player {
        return Player(readerController!!, displayController!!)
    }

    override val descriptor: SerialDescriptor = SerialDescriptor("player")
}

internal object SaveHandler {

    private const val SAVE_FILE_NAME = "save.txt"

    private fun polymorphicContext(readerController: ReaderController?, displayController: DisplayController?): SerialModule = SerializersModule {
        polymorphic(FieldObject::class, Character::class, AbstractMonster::class) {
            EmptyCell::class with EmptyCell.serializer()
            InvisibleCell::class with InvisibleCell.serializer()
            Wall::class with Wall.serializer()
            Equipment::class with Equipment.serializer()

            Player::class with PlayerSerializer(readerController, displayController)

            AggressiveMonster::class with AggressiveMonster.serializer()
            CowardMonster::class with CowardMonster.serializer()
            PassiveMonster::class with PassiveMonster.serializer()
        }

        polymorphic(Strategy::class) {
            AggressiveStrategy::class with AggressiveStrategy.serializer()
            CowardStrategy::class with CowardStrategy.serializer()
            PassiveStrategy::class with PassiveStrategy.serializer()
            ConfusedStrategy::class with ConfusedStrategy.serializer()
        }
    }

    fun saveGame(gameInfo: GameInfo) {
        val json = Json(
            context = polymorphicContext(null, null)
        )
        val jsonData = json.stringify(GameInfo.serializer(), gameInfo)
        File(SAVE_FILE_NAME).printWriter().use {
            it.println(jsonData)
        }
    }

    fun loadField(readerController: ReaderController, displayController: DisplayController): GameInfo {
        File(SAVE_FILE_NAME).bufferedReader().use {
            val data = it.readText()
            val json = Json(
                context = polymorphicContext(readerController, displayController)
            )
            return json.parse(GameInfo.serializer(), data)
        }
    }

    fun checkIfSaveExists(): Boolean {
        return File(SAVE_FILE_NAME).exists()
    }

    fun deleteSave() {
        File(SAVE_FILE_NAME).delete()
    }
}

@Serializable
data class GameInfo(
    val field: Field,
    val fieldInfos: Map<Int, Array<BooleanArray>>,
    val lastCharacterId: Int
)