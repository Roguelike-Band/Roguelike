package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.player.Player

class CowardStrategyTest {
    private lateinit var field: Field

    @BeforeEach
    fun initFieldInfo() {
        val mockedReader = Mockito.mock(ReaderController::class.java)

        field = Field(arrayOf(
            arrayOf(EmptyCell(),          EmptyCell(), Wall()),
            arrayOf(Player(mockedReader), EmptyCell(), EmptyCell()),
            arrayOf(Wall(),               EmptyCell(), EmptyCell()),
            arrayOf(Wall(),               EmptyCell(), EmptyCell())
        ))
    }

    @Test
    fun `Should move to father from player cell`() {
        val strategy = CowardStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }

        val coordinates = Coordinates(1, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(5)

        val newCoordinates = strategy.generateStep(fieldInfo)

        assertTrue(newCoordinates in listOf(Coordinates(0, 1), Coordinates(1, 2), Coordinates(2, 1)))
    }

    @Test
    fun `Should not move if in optimal cell`() {
        val strategy = CowardStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }

        val coordinates = Coordinates(0, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(5)

        val newCoordinates = strategy.generateStep(fieldInfo)

        assertTrue(newCoordinates in listOf(Coordinates(0, 1)))
    }

    @Test
    fun `Should not move if cannot see player`() {
        val strategy = CowardStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.COWARD_MONSTER
        }

        val coordinates = Coordinates(1, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(0)

        val newCoordinates = strategy.generateStep(fieldInfo)

        assertTrue(newCoordinates in listOf(Coordinates(1, 1)))
    }
}
