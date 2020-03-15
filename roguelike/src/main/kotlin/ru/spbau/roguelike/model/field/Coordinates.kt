package ru.spbau.roguelike.model.field

import kotlinx.serialization.Serializable

@Serializable
class Coordinates(val row: Int, val column: Int)