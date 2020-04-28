package ru.spbau.roguelike.model.field.objects.equipment

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import kotlin.random.Random

/**
 * Object for creating equipment.
 */
object EquipmentGenerator {
    /** Returns random equipment */
    fun generateRandom(): Equipment {
        return Equipment.Builder()
                .objectType(FieldObjectType.RANDOM_EQUIPMENT)
                .defenceDelta(Random.nextInt(-5, 6))
                .healthDelta(Random.nextInt(0, 3))
                .powerDelta(Random.nextInt(-2, 3))
                .build()
    }
}
