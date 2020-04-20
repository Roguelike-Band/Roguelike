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

    fun confuse(confusionTime: Int) {
        strategy = ConfusedStrategy(strategy, confusionTime, this)
    }

    fun unconfuse(previousStrategy: Strategy) {
        strategy = previousStrategy
    }

    fun hit(hitPower: Int) {
        attributes.hit(hitPower)
        println("new health is ${attributes.healthPoints}")
        if (attributes.healthPoints <= 0) {
            isAlive = false
        }
    }
}
