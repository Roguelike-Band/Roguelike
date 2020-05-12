package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.terminal.TerminalResizeListener
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.DisplayFieldInfo
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.player.Player
import ru.spbau.roguelike.model.field.objects.characters.player.PlayerView
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentListView
import java.lang.Integer.max
import java.lang.Integer.min

/**
 * Class for showing game status to console
 */
class ConsoleUIOutput(private val lanterna: Lanterna, private val status: UIStatus) {
    private var terminalSize = lanterna.getTerminalSize()

    companion object {
        const val MIN_TERMINAL_ROWS = 10
        const val MIN_TERMINAL_COLUMNS = 40
        const val EQUIPMENT_ROWS = 5
        const val ATTRIBUTES_COLUMNS = 20
    }

    init {
        lanterna.setResizeListener(TerminalResizeListener { _, newSize ->
            onTerminalResized(newSize)
        })
    }

    private fun onTerminalResized(newSize: TerminalSize) {
        terminalSize = newSize
        if (status.fieldInfo == null) {
            return
        }
        refreshGameField(status.fieldInfo!!, status.character!!)
    }

    /** Updates field on screen */
    fun refreshGameField(field: DisplayFieldInfo, player: PlayerView) {
        if (terminalSize.rows < MIN_TERMINAL_ROWS || terminalSize.columns < MIN_TERMINAL_COLUMNS) {
            return
        }

        refreshField(field, getFieldScreenPart())

        if (player.isAlive) {
            status.character = player
            status.equipmentList = player.equipmentList
            status.reloadCursor()
            refreshEquipment(status.equipmentList!!, getEquipmentScreenPart())

            val attributes = player.attributes
            refreshAttributes(attributes, getAttributesScreenPart())
        }

        lanterna.refreshScreen()
    }

    private fun refreshField(field: DisplayFieldInfo, screenPart: ScreenPart) {
        status.fieldInfo = field
        val fieldShow = getFieldShow(screenPart)
        for (fieldRow in fieldShow.leftRow..fieldShow.rightRow) {
            for (fieldColumn in fieldShow.leftColumn..fieldShow.rightColumn) {
                val cellType = field[Coordinates(fieldRow, fieldColumn)]
                lanterna.drawCell(
                        screenPart.leftColumn + fieldColumn - fieldShow.leftColumn,
                        screenPart.leftRow + fieldRow - fieldShow.leftRow,
                        CellDrawer.buildDrawingParameters(cellType)
                )
            }
        }
    }

    private fun getFieldScreenPart(): ScreenPart {
        return ScreenPart(
                leftRow = 0,
                rightRow = terminalSize.rows - EQUIPMENT_ROWS - 1,
                leftColumn = 0,
                rightColumn = terminalSize.columns - 1
        )
    }

    private fun refreshEquipment(equipment: EquipmentListView, screenPart: ScreenPart) {
        lanterna.fillRectangle(screenPart.leftColumn, screenPart.rightColumn, screenPart.leftRow, screenPart.rightRow, TextColor.ANSI.WHITE)

        lanterna.writeText(
                screenPart.leftColumn,
                screenPart.leftRow,
                TextParameters("Equipment:", TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)
        )

        val equipmentPart = getEquipmentShow(
                status.equipmentCursor,
                screenPart.rightColumn - screenPart.leftColumn + 1,
                equipment.size
        )
        for (equipmentId in equipmentPart.leftIndex..equipmentPart.rightIndex) {
            lanterna.drawCell(
                    screenPart.leftColumn + equipmentId - equipmentPart.leftIndex,
                    screenPart.leftRow + 1,
                    EquipmentDrawer.buildDrawingParameters(equipment[equipmentId].objectType, equipment.isPutOn(equipmentId))
            )
        }

        val cursorColumn = status.equipmentCursor - equipmentPart.leftIndex + screenPart.leftColumn
        lanterna.drawCell(
                cursorColumn,
                screenPart.leftRow + 2,
                DrawingParameters('^', TextColor.ANSI.CYAN, TextColor.ANSI.RED)
        )
    }

    private fun refreshAttributes(attributes: Attributes, screenPart: ScreenPart) {
        lanterna.apply {
            fillRectangle(screenPart.leftColumn, screenPart.rightColumn, screenPart.leftRow, screenPart.rightRow, TextColor.ANSI.WHITE)
            writeText(
                    screenPart.leftColumn + 1,
                    screenPart.leftRow + 1,
                    TextParameters("HP: ${attributes.healthPoints}", TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)
            )

            writeText(
                    screenPart.leftColumn + 1,
                    screenPart.leftRow + 2,
                    TextParameters("Power: ${attributes.maxPower}", TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)
            )

            writeText(
                    screenPart.leftColumn + 1,
                    screenPart.leftRow + 3,
                    TextParameters("Defence: ${attributes.defence}", TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)
            )
        }
    }

    private fun getEquipmentScreenPart(): ScreenPart {
        return ScreenPart(
                leftRow = terminalSize.rows - EQUIPMENT_ROWS,
                rightRow = terminalSize.rows - 1,
                leftColumn = ATTRIBUTES_COLUMNS,
                rightColumn = terminalSize.columns - 1
        )
    }

    private fun getAttributesScreenPart(): ScreenPart {
        return ScreenPart(
                leftRow = terminalSize.rows - EQUIPMENT_ROWS,
                rightRow = terminalSize.rows - 1,
                leftColumn = 0,
                rightColumn = ATTRIBUTES_COLUMNS - 1
        )
    }

    /** Returns part of field that will be shown on a screen */
    private fun getFieldShow(screenPart: ScreenPart): FieldPart {
        val (leftRow, rightRow) = getBorders(
            status.fieldInfo!!.coordinates.row,
            screenPart.rightRow - screenPart.leftRow + 1,
            status.fieldInfo!!.height
        )
        val (leftColumn, rightColumn) = getBorders(
            status.fieldInfo!!.coordinates.column,
            screenPart.rightColumn - screenPart.leftRow + 1,
            status.fieldInfo!!.width
        )
        return FieldPart(leftRow, rightRow, leftColumn, rightColumn)
    }

    private fun getEquipmentShow(center: Int, columns: Int, equipmentSize: Int): EquipmentPart {
        val (left, right) = getBorders(center, columns, equipmentSize)
        return EquipmentPart(left, right)
    }

    private fun getBorders(center: Int, totalSize: Int, fieldSize: Int): Pair<Int, Int> {
        var leftBorder = center - (totalSize - 1) / 2
        var rightBorder = leftBorder + totalSize - 1

        val leftOveruse = max(0, -leftBorder)
        val rightOveruse = max(0, rightBorder - (fieldSize - 1))
        rightBorder += leftOveruse
        leftBorder -= rightOveruse

        return Pair(max(0, leftBorder), min(fieldSize - 1, rightBorder))
    }

    private data class EquipmentPart(val leftIndex: Int, val rightIndex: Int)
    private data class FieldPart(val leftRow: Int, val rightRow: Int, val leftColumn: Int, val rightColumn: Int)
    private data class ScreenPart(val leftRow: Int, val rightRow: Int, val leftColumn: Int, val rightColumn: Int)
}
