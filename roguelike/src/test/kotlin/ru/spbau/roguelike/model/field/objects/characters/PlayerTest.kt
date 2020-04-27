package ru.spbau.roguelike.model.field.objects.characters

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.controller.Turn
import ru.spbau.roguelike.controller.turncommands.MoveUserCommand
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.player.Player

class PlayerTest {
    private lateinit var fieldInfo: FieldInfo
    private lateinit var movementExecutor: MovementExecutor

    @BeforeEach
    fun initFieldInfo() {
        val field = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(),
                Wall()
            ),
            arrayOf<FieldObject>(
                EmptyCell(),
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
        movementExecutor = MovementExecutor(field)
        fieldInfo = FieldInfo(field, Coordinates(1, 0))
        fieldInfo.setVisibleNeighbourhood(100)
    }

    @Test
    fun `Should not go left`() {
        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(MoveUserCommand(Turn.MOVEMENT_LEFT))

        val player = Player(mockedReader)
        fieldInfo[Coordinates(1, 0)] = player

        player.doTurn(fieldInfo, movementExecutor)

        val expected = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(), Wall()
            ),
            arrayOf(
                player,
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
        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(MoveUserCommand(Turn.MOVEMENT_RIGHT))

        val player = Player(mockedReader)
        fieldInfo[Coordinates(1, 0)] = player

        player.doTurn(fieldInfo, movementExecutor)

        val expected = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(), Wall()
            ),
            arrayOf(
                EmptyCell(), Player(mockedReader),
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
        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(MoveUserCommand(Turn.MOVEMENT_UP))

        val player = Player(mockedReader)
        fieldInfo[Coordinates(1, 0)] = player

        player.doTurn(fieldInfo, movementExecutor)

        val expected = Field(arrayOf(
            arrayOf(player, EmptyCell(), Wall()),
            arrayOf<FieldObject>(
                EmptyCell(),
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
        val mockedReader = mock(ReaderController::class.java)
        `when`(mockedReader.readTurn()).thenReturn(MoveUserCommand(Turn.MOVEMENT_DOWN))

        val player = Player(mockedReader)
        fieldInfo[Coordinates(1, 0)] = player

        player.doTurn(fieldInfo, movementExecutor)

        val expected = Field(arrayOf(
            arrayOf(
                EmptyCell(),
                EmptyCell(),
                Wall()
            ),
            arrayOf(
                player,
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