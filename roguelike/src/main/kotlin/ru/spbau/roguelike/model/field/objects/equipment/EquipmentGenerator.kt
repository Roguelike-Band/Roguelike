package ru.spbau.roguelike.model.field.objects.equipment

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import kotlin.random.Random

object EquipmentGenerator {
    fun generateRandom(): Equipment {
        return Equipment.Builder()
                .objectType(FieldObjectType.RANDOM_EQUIPMENT)
                .defenceDelta(Random.nextInt(-5, 6))
                .healthDelta(Random.nextInt(0, 3))
                .powerDelta(Random.nextInt(-2, 3))
                .build()
    }

    fun generateShield(): Equipment {
        return Equipment.Builder()
                .objectType(FieldObjectType.SHIELD)
                .defenceDelta(5)
                .powerDelta(-5)
                .healthDelta(0)
                .build()
    }
}