package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.FieldObjectType
import java.lang.IllegalStateException
import kotlin.random.Random

/** Factory for monsters */
object MonsterFactory {

    /** Create a monster of random type */
    fun generateMonster(): AbstractMonster {
        val monsters = listOf(
                FieldObjectType.PASSIVE_MONSTER,
                FieldObjectType.AGGRESSIVE_MONSTER,
                FieldObjectType.COWARD_MONSTER
        )
        val monster = monsters[Random.nextInt(monsters.size)]
        return when (monster) {
            FieldObjectType.PASSIVE_MONSTER -> PassiveMonster()
            FieldObjectType.AGGRESSIVE_MONSTER -> AggressiveMonster()
            FieldObjectType.COWARD_MONSTER -> CowardMonster()
            else -> throw IllegalStateException("Not a monster")
        }
    }
}