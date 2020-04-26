package ru.spbau.roguelike.model.field.objects.equipment

import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character

class Equipment private constructor(
        override val objectType: FieldObjectType,
        val defenceDelta: Int,
        val powerDelta: Int,
        val healthDelta: Int
) : FieldObject() {

    override fun onStep(character: Character): StepResult {
        character.addEquipment(this)
        return StepResult.STEP_SHOULD_BE_DONE
    }

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