package ru.spbau.roguelike.model.field.objects.equipment.items

import ru.spbau.roguelike.model.field.objects.FieldObject
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.FieldObjectType
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.equipment.Equipment

/** Sample equipment item */
class Shield : FieldObject(), Equipment {
    override val objectType = FieldObjectType.SHIELD

    override fun onStep(character: Character): StepResult {
        TODO("not implemented")
    }
}