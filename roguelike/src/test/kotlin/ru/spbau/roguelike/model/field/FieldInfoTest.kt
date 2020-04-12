package ru.spbau.roguelike.model.field

import org.junit.jupiter.api.BeforeEach
import ru.spbau.roguelike.model.field.objects.characters.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.*
import ru.spbau.roguelike.model.field.objects.cells.InvisibleCell

class FieldInfoTest {
    private lateinit var fieldInfo: FieldInfo

    @BeforeEach
    fun initFieldInfo() {
        val field = Field(arrayOf(
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
        fieldInfo = FieldInfo(field, Coordinates(1, 0))
    }

    @Test
    fun `At start all cells should be invisible`() {
        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                assertEquals(FieldObjectType.INVISIBLE_CELL, fieldInfo[Coordinates(row, column)].objectType)
            }
        }
    }

    @Test
    fun `Zero visibility should make visible only current cell`() {
        fieldInfo.setVisibleNeighbourhood(0)
        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                if (row != 1 || column != 0) {
                    assertEquals(FieldObjectType.INVISIBLE_CELL, fieldInfo[Coordinates(row, column)].objectType)
                } else {
                    assertEquals(FieldObjectType.PLAYER, fieldInfo[Coordinates(row, column)].objectType)
                }
            }
        }
    }

    @Test
    fun `Visibility should work`() {
        val expected = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(), InvisibleCell()
            ),
            arrayOf(Player(), EmptyCell(),
                InvisibleCell()
            ),
            arrayOf(Wall(), EmptyCell(),
                InvisibleCell()
            ),
            arrayOf<FieldObject>(
                InvisibleCell(),
                InvisibleCell(),
                InvisibleCell()
            )
        ))
        fieldInfo.setVisibleNeighbourhood(1)
        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                assertEquals(expected[Coordinates(row, column)].objectType, fieldInfo[Coordinates(row, column)].objectType)
            }
        }
    }
}