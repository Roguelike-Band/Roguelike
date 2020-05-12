package ru.spbau.roguelike.model.field.objects.equipment

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.characters.Attributes

/**
 * List of character's equipment.
 *
 * For each item we store if it is put on
 */
@Serializable
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

    /** Checks if item is already put on */
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

    /**
     * Puts item on applying effects to [attributes].
     *
     * If item cannot be put on, does nothing
     */
    fun putOn(index: Int, attributes: Attributes) {
        if (index !in 0 until size || isPutOn[index]) {
            return
        }
        if (attributes.addEquipment(allEquipment[index])) {
            isPutOn[index] = true
        }
    }

    /**
     * Takes item off applying effects to [attributes].
     *
     * If item cannot be taken off, does nothing
     */
    fun takeOff(index: Int, attributes: Attributes) {
        if (index !in 0 until size || !isPutOn[index]) {
            return
        }
        if (attributes.removeEquipment(allEquipment[index])) {
            isPutOn[index] = false
        }
    }

    fun toEquipmentListView(): EquipmentListView {
        return EquipmentListView(allEquipment, isPutOn)
    }
}

