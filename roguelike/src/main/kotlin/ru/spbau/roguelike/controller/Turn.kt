package ru.spbau.roguelike.controller

/** Types of turns */
enum class Turn {
    MOVEMENT_LEFT,
    MOVEMENT_RIGHT,
    MOVEMENT_UP,
    MOVEMENT_DOWN,
    PUT_ON_EQUIPMENT,
    TAKE_OFF_EQUIPMENT
}