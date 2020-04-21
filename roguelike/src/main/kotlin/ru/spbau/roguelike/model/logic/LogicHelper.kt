package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.MonsterFactory
import kotlin.random.Random

/** Helper methods for [Logic] */
internal class LogicHelper(
        private val field: Field,
        private val characters: MutableList<CharacterInfo>
) {
    private companion object {
        const val MONSTER_GENERATION_PERCENTAGE = 20
    }

    /** Update visibility ranges for all characters */
    internal fun setVisibilities() {
        for ((character, info) in characters.map { it.character to it.fieldInfo }) {
            info.setVisibleNeighbourhood(character.vision)
        }
    }

    /** Refresh player UI */
    internal fun refreshScreens() {
        for (afterTurnLogic in characters.map { it.afterTurnLogic }) {
            afterTurnLogic.refreshPlayerUI()
        }
    }

    /** Apply effects after turn, i.e. remove dead characters */
    internal fun updateAfterTurn() {
        deleteDead()
    }

    /**
     * Apply effects after epoch of turns,
     * i.e. spawn new monsters and refresh player's UI.
     */
    internal fun finishEpoch() {
        generateNewMonsters()
        refreshScreens()
    }

    private fun deleteDead() {
        for ((character, fieldInfo) in characters.map { it.character to it.fieldInfo }) {
            if (!character.isAlive && field[fieldInfo.coordinates] === character) {
                field[fieldInfo.coordinates] = EmptyCell()
            }
        }
    }

    private fun generateNewMonsters()  {
        if (Random.nextInt(100) < MONSTER_GENERATION_PERCENTAGE) {
            val monster = MonsterFactory.generateMonster()
            addMonster(monster)
        }
    }

    private fun addMonster(monster: AbstractMonster) {
        val coordinates = field.getRandomEmptyCell()
        field[coordinates] = monster
        val movementExecutor = MovementExecutor(field)
        val fieldInfo = FieldInfo(field, coordinates)
        val monsterInfo = CharacterInfo(
                isRealCharacter = false,
                fieldInfo = fieldInfo,
                character = monster,
                movementExecutor = movementExecutor,
                turnLogic = TurnLogic(monster, fieldInfo, movementExecutor),
                afterTurnLogic = MonsterAfterTurnLogic()
        )
        characters.add(monsterInfo)
    }
}