package ru.spbau.roguelike.model.field.objects.characters.monsters

import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Strategy

abstract class AbstractMonster(strategy: Strategy, attributes: Attributes) : Character(strategy, attributes) {
    override val vision = 5
}