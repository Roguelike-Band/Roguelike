package ru.spbau.roguelike.model.field.objects.equipment

import ru.spbau.roguelike.model.field.objects.characters.Attributes

/**
 * List of character's equipment.
 *
 * For each item we store if it
 */
class EquipmentList {
    private val allEquipment = arrayListOf<Equipment>()
    private val isPutOn = arrayListOf<Boolean>()
    val size: Int
        get() = allEquipment.size

    /** Returns immutable view on item for showing it to user */
    operator fun get(index: Int): Equipment {
        if (index !in 0 until size) {
            throw IllegalArgumentException("Equipment $index is out of bounds [0; $size)")
        }
        return allEquipment[index]
    }

    fun isPutOn(index: Int): Boolean {
        if (index !in 0 until size) {
            throw IllegalArgumentException("Equipment $index is out of bounds [0; $size)")
        }
        return isPutOn[index]
    }

    /** Adds new item as last element of the list. */
    fun addItem(item: Equipment) {
        allEquipment.add(item)
        isPutOn.add(false)
    }

    fun putOn(index: Int, attributes: Attributes) {
        if (index !in 0 until size || isPutOn[index]) {
            return
        }
        if (attributes.addEquipment(allEquipment[index])) {
            isPutOn[index] = true
        }
    }

    fun takeOff(index: Int, attributes: Attributes) {
        if (index !in 0 until size || !isPutOn[index]) {
            return
        }
        if (attributes.removeEquipment(allEquipment[index])) {
            isPutOn[index] = false
        }
    }
}

