package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable

/** Coordinates on a field */
@Serializable
class Coordinates(val row: Int, val column: Int)