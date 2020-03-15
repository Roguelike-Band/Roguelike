package ru.spbau.roguelike.model.field

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonInput
import kotlinx.serialization.json.JsonObject
import ru.spbau.roguelike.model.field.objects.EmptyCell
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.FieldObjectType.*
import ru.spbau.roguelike.model.field.objects.InvisibleCell
import ru.spbau.roguelike.model.field.objects.Wall
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Player
import ru.spbau.roguelike.model.field.objects.characters.monsters.DefaultMonster
import ru.spbau.roguelike.model.field.objects.equipment.items.Shield

@Polymorphic
abstract class FieldObject {
    abstract val objectType: FieldObjectType
    open val canBeCharacterStartCell = false

    abstract fun onStep(character: Character): StepResult

    @Serializer(forClass = FieldObject::class)
    companion object : KSerializer<FieldObject> {
        override val descriptor: SerialDescriptor =
            PrimitiveDescriptor("FieldObject", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: FieldObject) {
            when (value) {
                is EmptyCell -> encoder.encode(EmptyCell.serializer(), value)
                is Player -> encoder.encode(Player.serializer(), value)
                is Wall -> encoder.encode(Wall.serializer(), value)
            }
        }

        override fun deserialize(decoder: Decoder): FieldObject {
            val input = decoder as? JsonInput
                ?: throw SerializationException("Expected JsonInput for ${decoder::class}")
            val jsonObject = input.decodeJson() as? JsonObject
                ?: throw SerializationException("Expected JsonObject for ${input.decodeJson()::class}")

            return when (decoder.json.parse(
                FieldObjectType.serializer(),
                "\"${jsonObject.getPrimitive("objectType").content}\""
            )) {
                EMPTY_CELL ->
                    decoder.json.parse(EmptyCell.serializer(), jsonObject.toString())
                PLAYER -> decoder.json.parse(Player.serializer(), jsonObject.toString())
                WALL -> decoder.json.parse(Wall.serializer(), jsonObject.toString())
                DEFAULT_MONSTER -> decoder.json.parse(DefaultMonster.serializer(), jsonObject.toString())
                INVISIBLE_CELL -> decoder.json.parse(InvisibleCell.serializer(), jsonObject.toString())
                SHIELD -> decoder.json.parse(Shield.serializer(), jsonObject.toString())
            }
        }
    }
}