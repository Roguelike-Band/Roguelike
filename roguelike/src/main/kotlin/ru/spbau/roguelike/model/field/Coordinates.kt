package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable

/** Coordinates on a field */
@Serializable
data class Coordinates(val row: Int, val column: Int)