package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.MoveCommand
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.player.Player

class AggressiveStrategyTest {
    private lateinit var field: Field

    @BeforeEach
    fun initFieldInfo() {
        val mockedReader = Mockito.mock(ReaderController::class.java)
        val mockedDisplay = Mockito.mock(DisplayController::class.java)

        field = Field(arrayOf(
            arrayOf(EmptyCell(),          EmptyCell(), Wall()),
            arrayOf(Player(mockedReader, mockedDisplay), EmptyCell(), EmptyCell()),
            arrayOf(Wall(),               Wall(), EmptyCell()),
            arrayOf(Wall(),               EmptyCell(), EmptyCell())
        ))
    }

    @Test
    fun `Should attack player if can`() {
        val strategy = AggressiveStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.AGGRESSIVE_MONSTER
        }

        val coordinates = Coordinates(1, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(5)

        val newCoordinates = strategy.generateStep(fieldInfo) as MoveCommand

        assertTrue(newCoordinates.stepTo in listOf(Coordinates(1, 0)))
    }

    @Test
    fun `Should move even if not geometrical closest`() {
        val strategy = AggressiveStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.AGGRESSIVE_MONSTER
        }

        val coordinates = Coordinates(3, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(5)

        val newCoordinates = strategy.generateStep(fieldInfo) as MoveCommand

        assertTrue(newCoordinates.stepTo in listOf(Coordinates(3, 2)))
    }

    @Test
    fun `Should not move if cannot see player`() {
        val strategy = AggressiveStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.AGGRESSIVE_MONSTER
        }

        val coordinates = Coordinates(1, 1)
        field[coordinates] = newMonster
        val fieldInfo = FieldInfo(field, coordinates)
        fieldInfo.setVisibleNeighbourhood(0)

        val newCoordinates = strategy.generateStep(fieldInfo) as MoveCommand

        assertTrue(newCoordinates.stepTo in listOf(Coordinates(1, 1)))
    }
}