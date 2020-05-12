package ru.spbau.roguelike.model.field.objects.equipment

class EquipmentListView(
    private val allEquipment: ArrayList<Equipment>,
    private val isPutOn: ArrayList<Boolean>
) {
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
}