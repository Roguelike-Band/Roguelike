package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.EquipmentNavigatorMove
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldGenerationParameters
import ru.spbau.roguelike.model.field.FieldGenerator
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Player
import kotlin.random.Random

class Logic(
    private val displayController: DisplayController,
    private val readerController: ReaderController
) {

    private var field = FieldGenerator.generateField(
        FieldGenerationParameters(100, 100, 20)
    )
    private lateinit var turnLogic: TurnLogic
    private lateinit var afterTurnLogic: AfterTurnLogic
    private lateinit var player: Player

    fun gameLoop() {
        populateField()
        afterTurnLogic.refreshPlayerUI()
        while (true) {
            turnLogic.doTurn()
            afterTurnLogic.refreshPlayerUI()
        }
    }

    private fun populateField() {
        player = createPlayer()
        turnLogic = TurnLogic(GameInfo(field, player))
        afterTurnLogic = AfterTurnLogic(GameInfo(field, player), displayController)
    }

    private fun createPlayer(): Player {
        val possibleCoordinates = mutableListOf<Coordinates>()
        for (row in 0 until field.height) {
            for (column in 0 until field.width) {
                val coordinates = Coordinates(row, column)
                if (field[coordinates].canBeCharacterStartCell) {
                    possibleCoordinates.add(coordinates)
                }
            }
        }

        val position = possibleCoordinates[Random.nextInt(possibleCoordinates.size)]
        val player = Player(FieldInfo(field, position), readerController)
        field[position] = player

        return player
    }

    private fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        TODO("Not implemented")
    }

    private fun toNextLevel() {
        TODO("NOT IMPLEMENTED")
    }
}