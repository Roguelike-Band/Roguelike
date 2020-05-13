package ru.spbau.roguelike.net.grpc

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.FieldObjectType.*
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.CharacterView
import ru.spbau.roguelike.model.field.objects.characters.Command
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
import ru.spbau.roguelike.model.field.objects.characters.PutOnEquipmentCommand
import ru.spbau.roguelike.model.field.objects.characters.TakeOffEquipmentCommand
import ru.spbau.roguelike.model.field.objects.equipment.Equipment
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentListView

object ProtobufConverter {

    private val enumsMapping = listOf(
        EMPTY_CELL to ObjectType.EMPTY_CELL,
        INVISIBLE_CELL to ObjectType.INVISIBLE_CELL,
        WALL to ObjectType.WALL,
        PLAYER to ObjectType.PLAYER,
        AGGRESSIVE_MONSTER to ObjectType.AGGRESSIVE_MONSTER,
        COWARD_MONSTER to ObjectType.COWARD_MONSTER,
        PASSIVE_MONSTER to ObjectType.PASSIVE_MONSTER,
        SHIELD to ObjectType.SHIELD,
        USELESS_EQUIPMENT to ObjectType.USELESS_EQUIPMENT,
        RANDOM_EQUIPMENT to ObjectType.RANDOM_EQUIPMENT,
        ONLINE_PLAYER to ObjectType.ONLINE_PLAYER
    )

    private fun enumToProtobuf(fieldObjectType: FieldObjectType): ObjectType {
        for ((logic, proto) in enumsMapping) {
            if (logic == fieldObjectType) {
                return proto
            }
        }
        throw IllegalArgumentException("No such enum element $fieldObjectType")
    }

    fun convertCommand(protoCommand: ProtoCommand): Command = when {
        protoCommand.hasMoveCommand() -> {
            val moveCommand = protoCommand.moveCommand!!
            MoveCommand(Coordinates(moveCommand.stepTo.row, moveCommand.stepTo.column))
        }
        protoCommand.hasPutOnEquipmentCommand() -> {
            val putOnEquipmentCommand = protoCommand.putOnEquipmentCommand!!
            PutOnEquipmentCommand(putOnEquipmentCommand.index)
        }
        protoCommand.hasTakeOffEquipmentCommand() -> {
            val takeOffEquipmentCommand = protoCommand.takeOffEquipmentCommand!!
            TakeOffEquipmentCommand(takeOffEquipmentCommand.index)
        }
        else -> throw Error("Unexpected command")
    }

    fun convertToFieldInfoMessage(characterView: CharacterView, fieldInfo: DisplayFieldInfo): ProtoDisplayFieldInfo {
        val protobufFieldInfo = convertFieldInfo(fieldInfo)
        val protobufCharacter = convertCharacter(characterView)
        return ProtoDisplayFieldInfo.newBuilder()
            .setFieldInfo(protobufFieldInfo)
            .setPlayer(protobufCharacter)
            .build()
    }

    private fun convertCharacter(characterView: CharacterView): PlayerView {
        val protobufEquipmentList = convertEquipmentList(characterView.equipmentList)
        val protobufAttributes = convertAttributes(characterView.attributes)
        return PlayerView.newBuilder()
            .setAttributes(protobufAttributes)
            .setEquipmentList(protobufEquipmentList)
            .setIsAlive(characterView.isAlive)
            .build()
    }

    private fun convertAttributes(attributes: Attributes): ProtoAttributes {
        return ProtoAttributes.newBuilder()
            .setDefence(attributes.defence)
            .setHealth(attributes.healthPoints)
            .setMaxPower(attributes.maxPower)
            .build()
    }

    private fun convertEquipmentList(equipmentListView: EquipmentListView): ProtoEquipmentListView {
        val equipmentList = mutableListOf<ProtoEquipment>()
        val isPutOn = mutableListOf<Boolean>()
        for (equipmentId in 0 until equipmentListView.size) {
            equipmentList.add(convertEquipment(equipmentListView[equipmentId]))
            isPutOn.add(equipmentListView.isPutOn(equipmentId))
        }
        return ProtoEquipmentListView.newBuilder()
            .addAllAllEquipment(equipmentList)
            .addAllIsPutOn(isPutOn)
            .build()
    }

    private fun convertEquipment(equipment: Equipment): ProtoEquipment {
        return ProtoEquipment.newBuilder()
            .setObjectType(enumToProtobuf(equipment.objectType))
            .setDefenceDelta(equipment.defenceDelta)
            .setHealthDelta(equipment.healthDelta)
            .setPowerDelta(equipment.powerDelta)
            .build()
    }

    private fun convertFieldInfo(fieldInfo: DisplayFieldInfo): ProtoFieldInfo {
        val fieldInfoAsArray = Array(fieldInfo.height) { row ->
            Array(fieldInfo.width) { column ->
                fieldInfo[Coordinates(row, column)]
            }
        }

        return ProtoFieldInfo.newBuilder()
            .addAllField(
                fieldInfoAsArray.map {
                    ArrayRow.newBuilder()
                        .addAllObjectTypes(
                            it.map { cell -> enumToProtobuf(cell) }
                        )
                        .build()
                }
            )
            .build()
    }
}
