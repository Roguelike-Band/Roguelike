package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TextColor
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import java.lang.IllegalArgumentException

/** Object for drawing equipment in equipment list */
object EquipmentDrawer {
    /** Maps concrete object to drawing parameters */
    fun buildDrawingParameters(objectType: FieldObjectType, isPutOn: Boolean): DrawingParameters {
        val backgroundColor: TextColor.ANSI
        val foregroundColor: TextColor.ANSI

        if (isPutOn) {
            backgroundColor = TextColor.ANSI.GREEN
            foregroundColor = TextColor.ANSI.RED
        } else {
            backgroundColor = TextColor.ANSI.YELLOW
            foregroundColor = TextColor.ANSI.RED
        }

        return when (objectType) {
            FieldObjectType.SHIELD -> DrawingParameters('s', backgroundColor, foregroundColor)
            FieldObjectType.USELESS_EQUIPMENT -> TODO()
            FieldObjectType.RANDOM_EQUIPMENT -> DrawingParameters('r', backgroundColor, foregroundColor)
            else -> throw IllegalArgumentException("Unknown equipment $objectType")
        }
    }
}