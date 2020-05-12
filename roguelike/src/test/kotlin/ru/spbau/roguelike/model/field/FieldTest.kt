package ru.spbau.roguelike.model.field

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.monsters.PassiveMonster
import ru.spbau.roguelike.model.field.objects.characters.player.Player

class FieldTest {
    private lateinit var field: Field

    @BeforeEach
    fun initField() {
        val mockedReader = Mockito.mock(ReaderController::class.java)
        val mockedDisplay = Mockito.mock(DisplayController::class.java)

        field = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(),
                Wall()
            ),
            arrayOf(
                Player(mockedReader, mockedDisplay),
                EmptyCell(),
                EmptyCell()
            ),
            arrayOf(
                Wall(),
                EmptyCell(),
                EmptyCell()
            ),
            arrayOf(
                Wall(),
                EmptyCell(),
                EmptyCell()
            )
        ))
    }

    @Test
    fun `Should calculate dimensions correctly`() {
        assertEquals(4, field.height)
        assertEquals(3, field.width)
    }

    @Test
    fun `Get should work correctly`() {
        assertEquals(FieldObjectType.PLAYER, field[Coordinates(1, 0)].objectType)
        assertEquals(FieldObjectType.EMPTY_CELL, field[Coordinates(1, 1)].objectType)
    }

    @Test
    fun `Set should work correctly`() {
        field[Coordinates(2, 2)] = PassiveMonster()
        assertEquals(FieldObjectType.PASSIVE_MONSTER, field[Coordinates(2, 2)].objectType)
    }

    @Test
    fun `Move should work correctly`() {
        field.move(Coordinates(1, 0), Coordinates(1, 1))
        assertEquals(FieldObjectType.PLAYER, field[Coordinates(1, 1)].objectType)
        assertEquals(FieldObjectType.EMPTY_CELL, field[Coordinates(1, 0)].objectType)
    }
}