package ru.spbau.roguelike.ui

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.TerminalResizeListener

class Lanterna() {
    private val terminal: Terminal = DefaultTerminalFactory().createTerminal()
    private val screen  = TerminalScreen(terminal)
    private val textGraphics: TextGraphics = screen.newTextGraphics()

    init {
        screen.startScreen()
    }

    fun setResizeListener(resizeListener: TerminalResizeListener) {
        terminal.addResizeListener(resizeListener)
    }

    fun getTerminalSize(): TerminalSize {
        return terminal.terminalSize
    }

    fun ignoreAllPreviousInput() {
        var lastInput = terminal.pollInput()
        while (lastInput != null) {
            lastInput = terminal.pollInput()
        }
    }

    fun readInput(): KeyStroke {
        return terminal.readInput()
    }

    fun drawCell(column: Int, row: Int, cellParameters: DrawingParameters) {
        textGraphics.apply {
            val previousBackgroundColor = backgroundColor
            val previousForegroundColor = foregroundColor

            backgroundColor = cellParameters.backgroundColor
            foregroundColor = cellParameters.foregroundColor

            setCharacter(column, row, cellParameters.symbol)

            backgroundColor = previousBackgroundColor
            foregroundColor = previousForegroundColor
        }
    }

    fun refreshScreen() {
        screen.refresh()
        screen.doResizeIfNecessary()
    }
}