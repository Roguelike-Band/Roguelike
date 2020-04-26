package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.Serializable

/** State of a character */
@Serializable
class Attributes(private val mHealthPoints: Int, private val mMaxPower: Int) {

    /** Number of current health points of this character */
    var healthPoints = mHealthPoints
        private set

    /** Maximum hit power this character can attack with */
    var maxPower = mMaxPower
        private set

    /**
     * Take a hit. When this method is called, character's
     * health points decrease by [hitPower].
     * No checks are evaluated here.
     */
    fun hit(hitPower: Int) {
        healthPoints -= hitPower
    }
}