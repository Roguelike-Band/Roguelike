package ru.spbau.roguelike.model.field.objects.characters.player

import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.equipment.EquipmentListView

class PlayerView(
    val isAlive: Boolean,
    val attributes: Attributes,
    val equipmentList: EquipmentListView
)