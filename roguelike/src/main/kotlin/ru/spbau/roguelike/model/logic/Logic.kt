package ru.spbau.roguelike.model.logic

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.EquipmentNavigatorMove
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.*
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.MonsterFactory
import ru.spbau.roguelike.model.field.objects.characters.player.Player
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

        const val MONSTER_GENERATION_PERCENTAGE = 20
    }

    private var field = fileToLoadLevel?.let {
        FieldGenerator.loadField(File(it))
    } ?: FieldGenerator.generateField(
        FieldGenerationParameters(DEFAULT_FIELD_HEIGHT, DEFAULT_FIELD_WIDTH, DEFAULT_WALL_PERCENTAGE)
    )
    private lateinit var gameInfos: MutableList<GameInfo>
    private lateinit var turnLogics: MutableList<TurnLogic>
    private lateinit var afterTurnLogics: MutableList<AfterTurnLogic>

    fun gameLoop() {
        populateField()
        while (true) {
            for (logic in afterTurnLogics) {
                logic.refreshPlayerUI()
            }
            for (logic in turnLogics) {
                logic.doTurn()
                //saveGameInfo("save.txt")
                for (afterTurnLogic in afterTurnLogics) {
                    afterTurnLogic.refreshPlayerUI()
                }
            }
            generateNewMonsters()
        }
    }

    private fun generateNewMonsters() {
        if (Random.nextInt(100) < MONSTER_GENERATION_PERCENTAGE) {
            val monster = MonsterFactory.generateMonster()
            addMonster(monster)
        }
    }

    private fun addMonster(monster: AbstractMonster) {
        val coordinates = getStartingCoordinates()
        field[coordinates] = monster
        val movementExecutor = MovementExecutor(field)
        val gameInfo = GameInfo(FieldInfo(field, coordinates), monster, movementExecutor)
        gameInfo.fieldInfo.setVisibleNeighbourhood(monster.vision)

        turnLogics.add(TurnLogic(gameInfo))
        afterTurnLogics.add(MonsterAfterTurnLogic())
        gameInfos.add(gameInfo)
    }

    private fun populateField() {
        val coordinates = getStartingCoordinates()
        val movementExecutor = MovementExecutor(field)
        val player = createPlayer(coordinates)
        val gameInfo = GameInfo(FieldInfo(field, coordinates), player, movementExecutor)
        gameInfo.fieldInfo.setVisibleNeighbourhood(player.vision)
        turnLogics = mutableListOf(TurnLogic(gameInfo))
        afterTurnLogics = mutableListOf(PlayerAfterTurnLogic(gameInfo, displayController))

        gameInfos = mutableListOf(gameInfo)
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
        val player = Player(readerController)
        field[coordinates] = player

        return player
    }

    /*private fun saveGameInfo(fileName: String) {
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
            turnLogic = TurnLogic(gameInfo)
            afterTurnLogic = AfterTurnLogic(gameInfo, displayController)
        }
    }*/

    private fun onEquipmentNavigatorMove(equipmentNavigatorMove: EquipmentNavigatorMove) {
        TODO("Not implemented")
    }

    private fun toNextLevel() {
        TODO("NOT IMPLEMENTED")
    }
}