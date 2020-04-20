package ru.spbau.roguelike.model.field.objects.characters

class Attributes(mHealthPoints: Int, mMaxPower: Int) {
    var healthPoints = mHealthPoints
        private set
    var maxPower = mMaxPower
        private set

    fun hit(hitPower: Int) {
        healthPoints -= hitPower
    }
}