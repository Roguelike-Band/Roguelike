package ru.spbau.roguelike.ui

import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList
import java.lang.reflect.Field

class ConsoleUIOutput {

    fun refreshGameField(field: Field) {
        TODO("Not implemented")
    }

    fun refreshEquipmentList(equipmentList: EquipmentList) {
        TODO("Not implemented")
    }


}

fun main() {
    Lanterna.drawSomething()
    println(Lanterna.readInput())
}