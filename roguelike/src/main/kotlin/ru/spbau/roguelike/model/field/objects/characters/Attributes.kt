package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.equipment.Equipment

/** State of a character */
@Serializable
class Attributes(
    private val mHealthPoints: Int,
    private val mMaxPower: Int,
    private val mDefence: Int = 0
) {

    /** Number of current health points of this character */
    var healthPoints = mHealthPoints
        private set

    /** Maximum hit power this character can attack with */
    var maxPower = mMaxPower
        private set

    /** Hit power that charecter can nullify */
    var defence = mDefence
        private set

    /**
     * Take a hit. When this method is called, character's
     * health points decrease by [hitPower].
     * No checks are evaluated here.
     */
    fun hit(hitPower: Int) {
        healthPoints -= hitPower
    }

    fun addEquipment(item: Equipment): Boolean {
        if (defence + item.defenceDelta < 0) {
            return false
        }
        if (maxPower + item.powerDelta < 0) {
            return false
        }
        if (healthPoints + item.healthDelta < 0) {
            return false
        }

        defence += item.defenceDelta
        maxPower += item.powerDelta
        healthPoints += item.healthDelta
        return true
    }

    fun removeEquipment(item: Equipment): Boolean {
        if (defence - item.defenceDelta < 0) {
            return false
        }
        if (maxPower - item.powerDelta < 0) {
            return false
        }
        if (healthPoints - item.healthDelta < 0) {
            return false
        }

        defence -= item.defenceDelta
        maxPower -= item.powerDelta
        healthPoints -= item.healthDelta
        return true
    }
}