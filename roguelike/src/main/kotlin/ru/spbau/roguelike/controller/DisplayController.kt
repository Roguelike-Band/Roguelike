package ru.spbau.roguelike.controller

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList

interface DisplayController {
    fun refreshGameField(field: FieldInfo)
    fun refreshEquipmentList(equipmentList: EquipmentList)
    fun onGameIsFinished()
}