package ru.spbau.roguelike.model.field.objects.characters.monsters

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import ru.spbau.roguelike.model.field.objects.characters.Attributes
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.Strategy

/** Abstract class that all non-playable characters inherit */
@Serializable
abstract class AbstractMonster(private var _strategy: Strategy, @ContextualSerialization private val _attributes: Attributes) : Character(_strategy, _attributes) {
    override val vision = 5
}