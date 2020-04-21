package ru.spbau.roguelike.model.field.objects.characters

/** State of a character */
class Attributes(mHealthPoints: Int, mMaxPower: Int) {

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