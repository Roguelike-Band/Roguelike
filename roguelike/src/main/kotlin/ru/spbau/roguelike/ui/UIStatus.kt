package ru.spbau.roguelike.ui

import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.objects.characters.player.Player
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList

/**
 * Stores UI information (such as cursor position).
 */
class UIStatus {
    var equipmentCursor = 0
    var equipmentList: EquipmentList? = null
    var fieldInfo: DisplayFieldInfo? = null
    var character: Player? = null

    /**
     * Checks if cursor points on existing equipment item. If no, rearranges it.
     */
    fun reloadCursor() {
        if (equipmentList == null) {
            return
        }
        if (equipmentCursor < 0) {
            equipmentCursor = 0
        }
        val size = equipmentList!!.size
        if (size == 0) {
            equipmentCursor = 0
        } else if (equipmentCursor >= size) {
            equipmentCursor = size - 1
        }
    }

    fun moveCursorLeft() {
        --equipmentCursor
        reloadCursor()
    }

    fun moveCursorRight() {
        ++equipmentCursor
        reloadCursor()
    }
}