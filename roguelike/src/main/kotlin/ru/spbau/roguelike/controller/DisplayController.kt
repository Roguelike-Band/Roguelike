package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList

/** Interface for refreshing information from model to user */
interface DisplayController {
    /** Shows changes on field to user */
    fun refreshGameField(field: FieldInfo)
    fun refreshEquipmentList(equipmentList: EquipmentList)
    fun onGameIsFinished()
}