package ru.spbau.roguelike.model.field.objects.characters

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.FieldObject
import ru.spbau.roguelike.model.field.objects.EmptyCell
import ru.spbau.roguelike.model.field.objects.Wall

class PlayerTest {
    private lateinit var fieldInfo: FieldInfo

    @BeforeEach
    fun initFieldInfo() {
        val field = Field(arrayOf(
            arrayOf(EmptyCell(), EmptyCell(), Wall()),
            arrayOf(Player(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell())
        ))
        fieldInfo = FieldInfo(field, Coordinates(1, 0))
        fieldInfo.setVisibleNeighbourhood(100)
    }

    @Test
    fun `Should not go left`() {
        val player = fieldInfo[Coordinates(1, 0)] as Player

        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(Turn.MOVEMENT_LEFT)

        player.initializeReaderController(mockedReader)
        player.doTurn(fieldInfo)

        val expected = Field(arrayOf(
            arrayOf(EmptyCell(), EmptyCell(), Wall()),
            arrayOf(Player(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell())
        ))

        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                Assertions.assertEquals(
                    expected[Coordinates(row, column)].objectType,
                    fieldInfo[Coordinates(row, column)].objectType
                )
            }
        }
    }

    @Test
    fun `Should go right`() {
        val player = fieldInfo[Coordinates(1, 0)] as Player

        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(Turn.MOVEMENT_RIGHT)

        player.initializeReaderController(mockedReader)
        player.doTurn(fieldInfo)

        val expected = Field(arrayOf(
            arrayOf(EmptyCell(), EmptyCell(), Wall()),
            arrayOf(EmptyCell(), Player(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell())
        ))

        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                Assertions.assertEquals(
                    expected[Coordinates(row, column)].objectType,
                    fieldInfo[Coordinates(row, column)].objectType
                )
            }
        }
    }

    @Test
    fun `Should go up`() {
        val player = fieldInfo[Coordinates(1, 0)] as Player

        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(Turn.MOVEMENT_UP)

        player.initializeReaderController(mockedReader)
        player.doTurn(fieldInfo)

        val expected = Field(arrayOf(
            arrayOf(Player(), EmptyCell(), Wall()),
            arrayOf<FieldObject>(EmptyCell(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell())
        ))

        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                Assertions.assertEquals(
                    expected[Coordinates(row, column)].objectType,
                    fieldInfo[Coordinates(row, column)].objectType
                )
            }
        }
    }

    @Test
    fun `Should not go to the wall`() {
        val player = fieldInfo[Coordinates(1, 0)] as Player

        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(Turn.MOVEMENT_DOWN)

        player.initializeReaderController(mockedReader)
        player.doTurn(fieldInfo)

        val expected = Field(arrayOf(
            arrayOf(EmptyCell(), EmptyCell(), Wall()),
            arrayOf(Player(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell()),
            arrayOf(Wall(), EmptyCell(), EmptyCell())
        ))

        for (row in 0 until fieldInfo.height) {
            for (column in 0 until fieldInfo.width) {
                Assertions.assertEquals(
                    expected[Coordinates(row, column)].objectType,
                    fieldInfo[Coordinates(row, column)].objectType
                )
            }
        }
    }
}