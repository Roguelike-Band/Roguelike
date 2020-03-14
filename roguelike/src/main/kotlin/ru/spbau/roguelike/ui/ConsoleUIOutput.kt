package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.terminal.TerminalResizeListener
import ru.spbau.roguelike.model.field.Coordinates
import ru.spbau.roguelike.model.field.FieldInfo
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentList
import java.lang.Integer.max
import java.lang.Integer.min

class ConsoleUIOutput(private val lanterna: Lanterna) {
    private var field: FieldInfo? = null
    private var terminalSize = lanterna.getTerminalSize()

    init {
        lanterna.setResizeListener(TerminalResizeListener { _, newSize ->
            onTerminalResized(newSize)
        })
    }

    private fun onTerminalResized(newSize: TerminalSize) {
        terminalSize = newSize
        if (field == null) {
            return
        }
        refreshGameField(field!!)
    }

    fun refreshGameField(field: FieldInfo) {
        this.field = field
        val fieldShow = getFieldShow()
        for (row in fieldShow.leftRow..fieldShow.rightRow) {
            for (column in fieldShow.leftColumn..fieldShow.rightColumn) {
                val cellType = field[Coordinates(row, column)].objectType
                lanterna.drawCell(
                    column - fieldShow.leftColumn,
                    row - fieldShow.leftRow,
                    CellDrawer.buildDrawingParameters(cellType)
                )
            }
        }
        lanterna.refreshScreen()
    }

    /** Returns part of field that will be shown on a screen */
    private fun getFieldShow(): FieldPart {
        val (leftRow, rightRow) = getBorders(
            field!!.coordinates.row,
            terminalSize.rows,
            field!!.height
        )
        val (leftColumn, rightColumn) = getBorders(
            field!!.coordinates.column,
            terminalSize.columns,
            field!!.width
        )
        return FieldPart(leftRow, rightRow, leftColumn, rightColumn)
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

    fun refreshEquipmentList(equipmentList: EquipmentList) {
        TODO("Not implemented")
    }

    data class FieldPart(val leftRow: Int, val rightRow: Int, val leftColumn: Int, val rightColumn: Int)
}
