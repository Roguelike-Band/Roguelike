package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import org.junit.jupiter.api.Assertions.assertEquals
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

class PassiveStrategyTest {

    @Test
    fun `Should not move`() {
        val strategy = PassiveStrategy()

        val newMonster = object : AbstractMonster(strategy, Attributes(2, 3)) {
            override val objectType = FieldObjectType.PASSIVE_MONSTER
        }

        val mockedReader = Mockito.mock(ReaderController::class.java)
        val mockedDisplay = Mockito.mock(DisplayController::class.java)

        val field = Field(arrayOf(
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
                newMonster,
                EmptyCell()
            ),
            arrayOf(
                Wall(),
                EmptyCell(),
                EmptyCell()
            )
        ))
        val fieldInfo = FieldInfo(field, Coordinates(2, 1))

        val step = strategy.generateCommand(newMonster, fieldInfo) as MoveCommand

        assertEquals(Coordinates(2, 1), step.stepTo)
    }
}