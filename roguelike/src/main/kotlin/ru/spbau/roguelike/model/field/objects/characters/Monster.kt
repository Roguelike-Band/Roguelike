package ru.spbau.roguelike.model.field.objects.characters

abstract class Monster : Character() {
    abstract fun doTurn()
}