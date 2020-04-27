package ru.spbau.roguelike.model.logic

import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.Field
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.MovementExecutor
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.characters.monsters.AbstractMonster
import ru.spbau.roguelike.model.field.objects.characters.monsters.MonsterFactory
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentGenerator
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
        for (characterInfo in characters) {
            characterInfo.character.refreshPlayerUI(characterInfo.fieldInfo)
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
                field[fieldInfo.coordinates] = EquipmentGenerator.generateRandom()
            }
        }
    }

    private fun generateNewMonsters()  {
        if (Random.nextInt(100) < MONSTER_GENERATION_PERCENTAGE) {
            val monster = MonsterFactory.generateMonster()
            addMonster(monster)
        }
    }

    fun addMonster(
        monster: AbstractMonster,
        coordinates: Coordinates = field.getRandomEmptyCell(),
        fieldInfo: FieldInfo = FieldInfo(field, coordinates)
    ) {
        field[coordinates] = monster
        val movementExecutor = MovementExecutor(field)
        val monsterInfo = CharacterInfo(
                isRealCharacter = false,
                fieldInfo = fieldInfo,
                character = monster,
                movementExecutor = movementExecutor,
                turnLogic = TurnLogic(monster, fieldInfo, movementExecutor)
        )
        characters.add(monsterInfo)
    }
}