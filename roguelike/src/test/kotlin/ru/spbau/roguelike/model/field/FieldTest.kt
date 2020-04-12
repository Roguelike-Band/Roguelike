package ru.spbau.roguelike.model.field

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.Player

class FieldTest {
    private lateinit var field: Field

    @BeforeEach
    fun initField() {
        field = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(), Wall()
            ),
            arrayOf(Player(),
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
        field[Coordinates(2, 2)] = Player()
        assertEquals(FieldObjectType.PLAYER, field[Coordinates(2, 2)].objectType)
    }

    @Test
    fun `Move should work correctly`() {
        field.move(Coordinates(1, 0), Coordinates(1, 1))
        assertEquals(FieldObjectType.PLAYER, field[Coordinates(1, 1)].objectType)
        assertEquals(FieldObjectType.EMPTY_CELL, field[Coordinates(1, 0)].objectType)
    }
}