package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal

object Lanterna {
    private val terminal: Terminal = DefaultTerminalFactory().createTerminal()
    private val screen  = TerminalScreen(terminal)
    private val textGraphics: TextGraphics = screen.newTextGraphics()

    init {
        screen.startScreen()
    }

    fun readInput(): KeyStroke {
        return terminal.readInput()
    }

    fun drawSomething() {
        textGraphics.backgroundColor = TextColor.ANSI.MAGENTA
        textGraphics.putString(10, 10, "Hello, World!")
        textGraphics.backgroundColor = TextColor.ANSI.DEFAULT
        screen.refresh()
    }
}