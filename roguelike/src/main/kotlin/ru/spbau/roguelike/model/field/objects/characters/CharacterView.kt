package ru.spbau.roguelike.model.field.objects.characters

import ru.spbau.roguelike.model.field.objects.equipment.EquipmentListView

class CharacterView(
    val isAlive: Boolean,
    val attributes: Attributes,
    val equipmentList: EquipmentListView
)