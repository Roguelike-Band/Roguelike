package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.*
import ru.spbau.roguelike.model.field.objects.characters.player.Player
import java.io.File

/** Game logic */
class Logic(
    private val displayController: DisplayController,
    private val readerController: ReaderController,
    fileToLoadLevel: String? = null
) {

    private companion object {
        const val DEFAULT_FIELD_WIDTH = 100
        const val DEFAULT_FIELD_HEIGHT = 100
        const val DEFAULT_WALL_PERCENTAGE = 20
    }

    private val field = fileToLoadLevel?.let {
        FieldGenerator.loadField(File(it))
    } ?: FieldGenerator.generateField(
        FieldGenerationParameters(DEFAULT_FIELD_HEIGHT, DEFAULT_FIELD_WIDTH, DEFAULT_WALL_PERCENTAGE)
    )
    private val characters = mutableListOf<CharacterInfo>()
    private val logicHelper = LogicHelper(field, characters)

    fun gameLoop() {
        populateField()
        while (characters.filter { it.isRealCharacter }.any { it.character.isAlive }) {

            logicHelper.setVisibilities()
            logicHelper.refreshScreens()

            for (turnLogic in characters.map { it.turnLogic }) {
                turnLogic.doTurn()

                logicHelper.updateAfterTurn()
            }
            logicHelper.finishEpoch()
        }
    }

    private fun populateField() {
        val coordinates = field.getRandomEmptyCell()
        val movementExecutor = MovementExecutor(field)
        val player = createPlayer(coordinates)
        val fieldInfo = FieldInfo(field, coordinates)
        val characterInfo = CharacterInfo(
                isRealCharacter = true,
                fieldInfo = fieldInfo,
                character = player,
                movementExecutor = movementExecutor,
                turnLogic = TurnLogic(player, fieldInfo, movementExecutor),
                afterTurnLogic = PlayerAfterTurnLogic(fieldInfo, displayController)
        )
        characters.add(characterInfo)
    }

    private fun createPlayer(coordinates: Coordinates): Player {
        val player = Player(readerController)
        field[coordinates] = player

        return player
    }
}
