package ru.spbau.roguelike.model.field.objects.characters.monsters.strategies

import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import java.util.ArrayDeque

class BFS(
    private val fieldInfo: FieldInfo
) {

    private data class BFSResult(
        val visited: Set<Coordinates>,
        val depth: Map<Coordinates, Int>,
        val previous: Map<Coordinates, Coordinates>
    )

    private val bfsQueue = ArrayDeque<Coordinates>()

    private fun getMoveVariants(
        currentCell: Coordinates,
        visited: Set<Coordinates>
    ): List<Coordinates> {
        val variants = mutableListOf<Coordinates>()
        for (rowDiff in -1..1) {
            for (columnDiff in -1..1) {
                if (rowDiff == 0 || columnDiff == 0) {
                    val newCell = Coordinates(
                        currentCell.row + rowDiff,
                        currentCell.column + columnDiff
                    )
                    if (fieldInfo.isGood(newCell) &&
                        fieldInfo[newCell].objectType != FieldObjectType.WALL &&
                        fieldInfo[newCell].objectType != FieldObjectType.INVISIBLE_CELL &&
                        !visited.contains(newCell)
                    ) {
                        variants += newCell
                    }
                }
            }
        }
        return variants
    }

    private fun launch(from: Coordinates): BFSResult {
        val visited = mutableSetOf<Coordinates>()
        val depth = mutableMapOf<Coordinates, Int>()
        val previous = mutableMapOf<Coordinates, Coordinates>()
        bfsQueue += from
        depth[from] = 0
        while (bfsQueue.isNotEmpty()) {
            val currentCell = bfsQueue.poll()
            for (newCell in getMoveVariants(currentCell, visited)) {
                bfsQueue += newCell
                visited += newCell
                depth[newCell] = depth[currentCell]!! + 1
                previous[newCell] = currentCell
            }
        }
        return BFSResult(
            visited,
            depth,
            previous
        )
    }

    fun getCloserToPlayer(): Coordinates? {
        val bfsResult = launch(fieldInfo.coordinates)
        val playerCell = bfsResult.visited.find {
            fieldInfo[it].objectType == FieldObjectType.PLAYER
        } ?: return null
        var currentCell: Coordinates = playerCell
        val reversedRoute = mutableListOf<Coordinates>()
        while (currentCell != fieldInfo.coordinates) {
            reversedRoute += currentCell
            currentCell = bfsResult.previous[currentCell]!!
        }
        return reversedRoute.last()
    }

    fun getFartherFromPlayer(): Coordinates? {
        val bfsResultFromMonster = launch(fieldInfo.coordinates)
        val playerCell = bfsResultFromMonster.visited.find {
            fieldInfo[it].objectType == FieldObjectType.PLAYER
        } ?: return null
        val bfsResultFromPlayer = launch(playerCell)
        return getMoveVariants(fieldInfo.coordinates, setOf()).maxBy {
            bfsResultFromPlayer.depth[it]!!
        }
    }
}