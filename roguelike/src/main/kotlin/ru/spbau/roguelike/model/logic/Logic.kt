package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.controller.DisplayController
import ru.spbau.roguelike.controller.EquipmentNavigatorMove
import ru.spbau.roguelike.controller.ReaderController
import ru.spbau.roguelike.model.field.*
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
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

    private val field: Field
    private val characters: MutableList<CharacterInfo> = mutableListOf()

    init {
        if (shouldLoadFromSave) {
            field = SaveHandler.loadField(readerController, displayController)
        } else {
            field = fileToLoadLevel?.let {
                FieldGenerator.loadField(File(it))
            } ?: FieldGenerator.generateField(
                FieldGenerationParameters(Logic.DEFAULT_FIELD_HEIGHT, Logic.DEFAULT_FIELD_WIDTH, Logic.DEFAULT_WALL_PERCENTAGE)
            )
            characters += createPlayer()
        }
    }

    private val logicHelper = LogicHelper(field, characters)

    init {
        if (shouldLoadFromSave) {
            for (i in 0 until field.height) {
                for (j in 0 until field.width) {
                    val cell = field[Coordinates(i, j)]
                    if (cell is Player) {
                        characters += createPlayer(
                            cell,
                            Coordinates(i, j)
                        )
                    }
                    if (cell is AbstractMonster) {
                        logicHelper.addMonster(
                            cell
                        )
                    }
                }
            }
        }
    }

    fun gameLoop() {
        while (characters.filter { it.isRealCharacter }.any { it.character.isAlive }) {

            logicHelper.setVisibilities()
            logicHelper.refreshScreens()

            for (turnLogic in characters.map { it.turnLogic }) {
                turnLogic.doTurn()

                logicHelper.updateAfterTurn()
            }
            logicHelper.finishEpoch()
            SaveHandler.saveField(field)
        }
    }

    private fun createPlayer(
        player: Player = Player(readerController, displayController),
        coordinates: Coordinates = field.getRandomEmptyCell()
    ): CharacterInfo {
        val movementExecutor = MovementExecutor(field)
        field[coordinates] = player
        val fieldInfo = FieldInfo(field, coordinates)
        return CharacterInfo(
            isRealCharacter = true,
            fieldInfo = fieldInfo,
            character = player,
            movementExecutor = movementExecutor,
            turnLogic = TurnLogic(player, fieldInfo, movementExecutor)
        )
    }
}
