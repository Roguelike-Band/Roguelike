package ru.spbau.roguelike.model.logic

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.EquipmentNavigatorMove
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldGenerationParameters
import ru.spbau.roguelike.model.field.FieldGenerator
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Player
import java.io.File
import kotlin.random.Random

/** Game logic */
class Logic(
    private val displayController: DisplayController,
    private val readerController: ReaderController,
    fileToLoadLevel: String? = null
) {

    companion object {
        const val DEFAULT_FIELD_WIDTH = 100
        const val DEFAULT_FIELD_HEIGHT = 100
        const val DEFAULT_WALL_PERCENTAGE = 20
    }

    private var field = fileToLoadLevel?.let {
        FieldGenerator.loadField(File(it))
    } ?: FieldGenerator.generateField(
        FieldGenerationParameters(DEFAULT_FIELD_HEIGHT, DEFAULT_FIELD_WIDTH, DEFAULT_WALL_PERCENTAGE)
    )
    private lateinit var gameInfo: GameInfo
    private lateinit var turnLogic: TurnLogic
    private lateinit var afterTurnLogic: AfterTurnLogic

    fun gameLoop() {
        populateField()
        afterTurnLogic.refreshPlayerUI()
        while (true) {
            turnLogic.doTurn()
            saveGameInfo("save.txt")
            afterTurnLogic.refreshPlayerUI()
        }
    }

    private fun populateField() {
        val coordinates = getStartingCoordinates()
        gameInfo = GameInfo(FieldInfo(field, coordinates), createPlayer(coordinates))
        gameInfo.fieldInfo.setVisibleNeighbourhood()
        turnLogic = TurnLogic(gameInfo)
        afterTurnLogic = AfterTurnLogic(gameInfo, displayController)
    }

    private fun getStartingCoordinates(): Coordinates {
        val possibleCoordinates = mutableListOf<Coordinates>()
        for (row in 0 until field.height) {
            for (column in 0 until field.width) {
                val coordinates = Coordinates(row, column)
                if (field[coordinates].canBeCharacterStartCell) {
                    possibleCoordinates.add(coordinates)
                }
            }
        }

        return possibleCoordinates[Random.nextInt(possibleCoordinates.size)]
    }

    private fun createPlayer(coordinates: Coordinates): Player {
        val player = Player()
        player.initializeReaderController(readerController)
        field[coordinates] = player

        return player
    }

    private fun saveGameInfo(fileName: String) {
        val json = Json(JsonConfiguration.Stable)
        val jsonData = json.stringify(GameInfo.serializer(), gameInfo)
        File(fileName).printWriter().use {
            it.println(jsonData)
        }
    }

    private fun loadGameInfo(fileName: String) {
        File(fileName).bufferedReader().use {
            val data = it.readText()
            val json = Json(JsonConfiguration.Stable)
            gameInfo = json.parse(GameInfo.serializer(), data)
            gameInfo.player.initializeReaderController(readerController)
            turnLogic = TurnLogic(gameInfo)
            afterTurnLogic = AfterTurnLogic(gameInfo, displayController)
        }
    }

    private fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        TODO("Not implemented")
    }

    private fun toNextLevel() {
        TODO("NOT IMPLEMENTED")
    }
}