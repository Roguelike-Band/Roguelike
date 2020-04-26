package ru.spbau.roguelike.model.field.objects

import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.PrimitiveDescriptor
import kotlinx.serialization.PrimitiveKind
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.Transient
import kotlinx.serialization.encode
import kotlinx.serialization.modules.SerializersModule
import ru.spbau.roguelike.model.field.StepResult
import ru.spbau.roguelike.model.field.objects.cells.EmptyCell
import ru.spbau.roguelike.model.field.objects.cells.InvisibleCell
import ru.spbau.roguelike.model.field.objects.cells.Wall
import ru.spbau.roguelike.model.field.objects.characters.Character
import ru.spbau.roguelike.model.field.objects.characters.player.Player

/** Every cell on the field is inheritor of this class */
@Serializable
abstract class FieldObject {
    abstract val objectType: FieldObjectType

    @Transient
    open val canBeCharacterStartCell = false

    /**
     * Does all side effects of character's try to step
     * and returns if step should be done
     */
    abstract fun onStep(character: Character): StepResult
}