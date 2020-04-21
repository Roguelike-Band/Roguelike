package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.characters.battle.BattleExecutor

/** Any alive character on a field */
abstract class Character(
        private var strategy: Strategy,
        val attributes: Attributes
) : FieldObject() {
    var isAlive: Boolean = true
        protected set

    abstract val vision: Int
    /** Method that is called by logic to make a turn */
    fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        val stepTo = strategy.generateStep(fieldInfo)
        movementExecutor.executeMove(this, stepTo, fieldInfo)
    }

    override fun onStep(character: Character): StepResult {
        BattleExecutor.executeBattle(character, this)
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    /**
     * When this method is called, character's strategy will be replaced with
     * [ConfusedStrategy] for given number of turns.
     */
    fun confuse(confusionTime: Int) {
        strategy = ConfusedStrategy(strategy, confusionTime, this)
    }

    /**
     * Return to standard strategy after spending in [ConfusedStrategy]
     * all the time character should spend there.
     */
    fun unconfuse(previousStrategy: Strategy) {
        strategy = previousStrategy
    }

    /**
     * Take a hit. When this method is called, character's
     * health points decrease by [hitPower].
     * If health points reached 0, this character dies.
     */
    fun hit(hitPower: Int) {
        attributes.hit(hitPower)
        println("new health is ${attributes.healthPoints}")
        if (attributes.healthPoints <= 0) {
            isAlive = false
        }
    }
}
