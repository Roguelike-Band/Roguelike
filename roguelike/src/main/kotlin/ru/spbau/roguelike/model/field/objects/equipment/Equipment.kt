package ru.spbau.roguelike.model.field.objects.equipment

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

/**
 * Equipment item.
 *
 * Equipment changes character's attributes.
 * For example, if character has 8 hp and equipment has 3 as [healthDelta], after applying
 * character will have 11 hp. Deltas may be negative.
 *
 * All equipment items should be created using [Equipment.Builder]
 */
@Serializable
class Equipment(
        override val objectType: FieldObjectType,
        val defenceDelta: Int,
        val powerDelta: Int,
        val healthDelta: Int
) : FieldObject() {

    override fun onStep(character: Character): StepResult {
        character.addEquipment(this)
        return StepResult.STEP_SHOULD_BE_DONE
    }

    /**
     * Class for creating equipment.
     *
     * To create an equipment you may use it like
     * val equipment = Equipment.Builder()
     *                          .defenceDelta(3)
     *                          .objectType(FieldObjectType.RANDOM_EQUIPMENT)
     *                          .powerDelta(42)
     *                          .healthDelta(-42)
     *                          .build()
     */
    class Builder {
        private var objectType: FieldObjectType = FieldObjectType.USELESS_EQUIPMENT
        private var defenceDelta: Int = 0
        private var powerDelta: Int = 0
        private var healthDelta: Int = 0

        fun objectType(type: FieldObjectType) = apply {
            objectType = type
        }

        fun defenceDelta(defenceDelta: Int) = apply {
            this.defenceDelta = defenceDelta
        }

        fun powerDelta(powerDelta: Int) = apply {
            this.powerDelta = powerDelta
        }

        fun healthDelta(healthDelta: Int) = apply {
            this.healthDelta = healthDelta
        }

        fun build(): Equipment {
            return Equipment(objectType, defenceDelta, powerDelta, healthDelta)
        }
    }
}
