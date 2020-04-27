package ru.spbau.roguelike.model.field.objects.characters.battle

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.strategies.PassiveStrategy

class BattleTest {
    @Test
    fun `Should perform two hits`() {
        val firstCharacter = object : AbstractMonster(PassiveStrategy(), Attributes(6, 5)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }
        val secondCharacter = object : AbstractMonster(PassiveStrategy(), Attributes(6, 5)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }
        BattleExecutor.executeBattle(firstCharacter, secondCharacter)

        assertTrue(firstCharacter.attributes.healthPoints <= 6 && secondCharacter.attributes.healthPoints <= 6)
    }

    @Test
    fun `Should perform only one hit if opponent died`() {
        val firstCharacter = object : AbstractMonster(PassiveStrategy(), Attributes(6, 5)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }
        val secondCharacter = object : AbstractMonster(PassiveStrategy(), Attributes(0, 5)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }
        BattleExecutor.executeBattle(firstCharacter, secondCharacter)

        assertFalse(secondCharacter.isAlive)
        assertTrue(secondCharacter.attributes.healthPoints <= 0)
        assertTrue(firstCharacter.attributes.healthPoints == 6)
    }
}
