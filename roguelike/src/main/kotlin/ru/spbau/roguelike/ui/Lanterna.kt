package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

object Lanterna {
    val terminal: Terminal = DefaultTerminalFactory().createTerminal()
    val screen: TerminalScreen = TerminalScreen(terminal)
    val textGraphics: TextGraphics = screen.newTextGraphics()

    init {
        screen.startScreen()
    }

    fun readInput(): Char {
        return terminal.readInput().character
    }

    fun drawSomething() {
        textGraphics.backgroundColor = TextColor.ANSI.MAGENTA
        textGraphics.putString(10, 10, "Hello, World!")
        textGraphics.backgroundColor = TextColor.ANSI.DEFAULT
        screen.refresh()
    }
}