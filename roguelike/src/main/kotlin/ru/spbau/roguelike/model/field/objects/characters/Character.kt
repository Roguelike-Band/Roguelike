package ru.spbau.roguelike.model.field.objects.characters

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.characters.battle.BattleExecutor
import ru.spbau.roguelike.model.field.objects.equipment.Equipment
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList

/** Any alive character on a field */
@Serializable
abstract class Character(
        var strategy: Strategy,
        @ContextualSerialization var attributes: Attributes
) : FieldObject() {
    @Transient
    var isAlive: Boolean = true
        protected set

    abstract val vision: Int

    var equipmentList = EquipmentList()

    val id = lastCharacterId++

    companion object {
        var lastCharacterId: Int = 0
    }

    /** Method that is called by logic to make a turn */
    fun doTurn(fieldInfo: FieldInfo, movementExecutor: MovementExecutor) {
        val command = strategy.generateCommand(this, fieldInfo)
        command.execute(this, movementExecutor, fieldInfo)
    }

    open fun refreshPlayerUI(fieldInfo: FieldInfo) {

    }

    override fun onStep(character: Character): StepResult {
        BattleExecutor.executeBattle(character, this)
        return StepResult.STEP_SHOULD_BE_CANCELLED
    }

    /** Puts [item] to character's equipment list */
    fun addEquipment(item: Equipment) {
        equipmentList.addItem(item)
    }

    fun putOnEquipment(indexInEquipmentList: Int) {
        equipmentList.putOn(indexInEquipmentList, attributes)
    }

    fun takeOffEquipment(indexInEquipmentList: Int) {
        equipmentList.takeOff(indexInEquipmentList, attributes)
    }

    /**
     * When this method is called, character's strategy will be replaced with
     * [ConfusedStrategy] for given number of turns.
     */
    fun confuse(confusionTime: Int) {
        strategy = ConfusedStrategy(strategy, confusionTime)
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
