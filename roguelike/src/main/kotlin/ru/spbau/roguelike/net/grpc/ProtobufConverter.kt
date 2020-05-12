package ru.spbau.roguelike.net.grpc

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.FieldObjectType.*
import ru.spbau.roguelike.model.field.objects.characters.CharacterView

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


    fun convertToFieldInfoMessage(characterView: CharacterView, fieldInfo: DisplayFieldInfo) {
        val protobufFieldInfo = convertFieldInfo(fieldInfo)
        val protobufCharacter = convertCharacter(characterView)
    }

    private fun convertCharacter(characterView: CharacterView): PlayerView {
       val protobufEquipmentList = convertEquipmentList(characterView.equipmentList)
        TODO()
    }

    private fun convertEquipmentList(equipmentListView: ru.spbau.roguelike.model.field.objects.equipment.EquipmentListView): EquipmentListView {
        TODO()
    }

    private fun convertFieldInfo(fieldInfo: DisplayFieldInfo): FieldInfo {
        val fieldInfoAsArray = Array(fieldInfo.height) { row ->
            Array(fieldInfo.width) { column ->
                fieldInfo[Coordinates(row, column)]
            }
        }

        return FieldInfo.newBuilder()
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