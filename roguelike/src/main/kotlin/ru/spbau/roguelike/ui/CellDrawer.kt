package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TextColor
import ru.spbau.roguelike.model.field.objects.FieldObjectType

/**
 * Object that maps field object to it's console drawing parameters
 */
object CellDrawer {
    /** Maps concrete object to drawing parameters */
    fun buildDrawingParameters(objectType: FieldObjectType): DrawingParameters {
        return when (objectType) {
            FieldObjectType.EMPTY_CELL -> DrawingParameters(' ')
            FieldObjectType.INVISIBLE_CELL -> DrawingParameters('?', TextColor.ANSI.BLUE)
            FieldObjectType.WALL -> DrawingParameters('W')
            FieldObjectType.PLAYER -> DrawingParameters('@', TextColor.ANSI.GREEN, TextColor.ANSI.RED)
            FieldObjectType.AGGRESSIVE_MONSTER -> DrawingParameters('A', TextColor.ANSI.YELLOW, TextColor.ANSI.RED)
            FieldObjectType.PASSIVE_MONSTER -> DrawingParameters('P', TextColor.ANSI.YELLOW, TextColor.ANSI.RED)
            FieldObjectType.COWARD_MONSTER -> DrawingParameters('C', TextColor.ANSI.YELLOW, TextColor.ANSI.RED)
            FieldObjectType.SHIELD -> DrawingParameters('s', TextColor.ANSI.BLACK, TextColor.ANSI.RED)
            FieldObjectType.USELESS_EQUIPMENT -> TODO()
            FieldObjectType.RANDOM_EQUIPMENT -> DrawingParameters('r', TextColor.ANSI.BLACK, TextColor.ANSI.RED)
            FieldObjectType.OPPONENT -> DrawingParameters('O', TextColor.ANSI.YELLOW, TextColor.ANSI.RED)
        }
    }
}

