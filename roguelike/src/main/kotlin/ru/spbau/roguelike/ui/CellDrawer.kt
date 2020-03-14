package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TextColor
import ru.spbau.roguelike.model.field.objects.FieldObjectType

object CellDrawer {
    fun buildDrawingParameters(objectType: FieldObjectType): DrawingParameters {
        return when (objectType) {
            FieldObjectType.EMPTY_CELL -> DrawingParameters(' ')
            FieldObjectType.INVISIBLE_CELL -> DrawingParameters('?', TextColor.ANSI.BLUE)
            FieldObjectType.WALL -> DrawingParameters('W')
            FieldObjectType.PLAYER -> DrawingParameters('@', TextColor.ANSI.GREEN, TextColor.ANSI.RED)
            FieldObjectType.DEFAULT_MONSTER -> TODO()
            FieldObjectType.SHIELD -> TODO()
        }
    }
}

class DrawingParameters(
    val symbol: Char,
    val backgroundColor: TextColor = TextColor.ANSI.DEFAULT,
    val foregroundColor: TextColor = TextColor.ANSI.DEFAULT
)
