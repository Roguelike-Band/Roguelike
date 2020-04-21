package ru.spbau.roguelike.model.logic

import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.MonsterFactory
import kotlin.random.Random

class LogicHelper(
        private val field: Field,
        private val characters: MutableList<CharacterInfo>
) {
    private companion object {
        const val MONSTER_GENERATION_PERCENTAGE = 20
    }

    fun setVisibilities() {
        for ((character, info) in characters.map { it.character to it.fieldInfo }) {
            info.setVisibleNeighbourhood(character.vision)
        }
    }

    fun refreshScreens() {
        for (afterTurnLogic in characters.map { it.afterTurnLogic }) {
            afterTurnLogic.refreshPlayerUI()
        }
    }

    fun updateAfterTurn() {
        deleteDead()
    }

    fun finishEpoch() {
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