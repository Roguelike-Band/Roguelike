package ru.spbau.roguelike.model.field.objects.characters.battle

import ru.spbau.roguelike.model.field.objects.characters.Character
import java.lang.Integer.max
import kotlin.random.Random

/** Battle-related functions. */
object BattleExecutor {

    private const val MAX_CONFUSION_TIME = 10

    /**
     * Applies the effects of attacking [firstCharacter] to [secondCharacter]
     * and after that effects of attacking [secondCharacter] to [firstCharacter]
     */
    fun executeBattle(firstCharacter: Character, secondCharacter: Character) {
        attack(firstCharacter, secondCharacter)
        if (secondCharacter.isAlive) {
            attack(secondCharacter, firstCharacter)
        }
    }

    private fun attack(attackingCharacter: Character, defensingCharacter: Character) {
        val attackHitPower = Random.nextInt(attackingCharacter.attributes.maxPower) + 1
        val defence = Random.nextInt(0, defensingCharacter.attributes.defence + 1)

        val softenHitPower = max(0, attackHitPower - defence)
        val isConfusing = Random.nextBoolean()

        if (isConfusing) {
            defensingCharacter.confuse(Random.nextInt(MAX_CONFUSION_TIME) + 1)
        }
        defensingCharacter.hit(softenHitPower)

        println("${attackingCharacter.objectType} attacks ${defensingCharacter.objectType} confusing=$isConfusing power=$attackHitPower defence=$defence")
    }
}
