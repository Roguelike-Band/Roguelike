package ru.spbau.roguelike.ui

import com.googlecode.lanterna.SGR
import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder
import com.googlecode.lanterna.gui2.dialogs.MessageDialog
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import com.googlecode.lanterna.terminal.TerminalResizeListener

/**
 * Wrapper above Lanterna library
 */
class Lanterna {
    private val terminal: Terminal = DefaultTerminalFactory()
        .setTerminalEmulatorTitle("Roguelike")
        .createTerminal()
    private val screen  = TerminalScreen(terminal)
    private val textGraphics: TextGraphics = screen.newTextGraphics()

    init {
        screen.startScreen()
        screen.cursorPosition = null
    }

    fun setResizeListener(resizeListener: TerminalResizeListener) {
        terminal.addResizeListener(resizeListener)
    }

    /** Returns current terminal size */
    fun getTerminalSize(): TerminalSize {
        return terminal.terminalSize
    }

    /** Non-blocking ignore of all previous input */
    fun ignoreAllPreviousInput() {
        var lastInput = terminal.pollInput()
        while (lastInput != null) {
            lastInput = terminal.pollInput()
        }
    }

    /** Blocking input read */
    fun readInput(): KeyStroke {
        return terminal.readInput()
    }

    /** Horizontally writes text on screen starting from <[row], [column]> */
    fun writeText(column: Int, row: Int, textParameters: TextParameters) {
        for ((char, index) in textParameters.text.toCharArray() zip textParameters.text.indices) {
            drawCell(
                    column + index,
                    row,
                    DrawingParameters(char, textParameters.backgroundColor, textParameters.foregroundColor)
            )
        }
    }

    fun fillRectangle(leftColumn: Int, rightColumn: Int, leftRow: Int, rightRow: Int, color: TextColor.ANSI) {
        textGraphics.apply {
            val previousBackgroundColor = backgroundColor

            backgroundColor = color
            fillRectangle(
                    TerminalPosition(leftColumn, leftRow),
                    TerminalSize(rightColumn - leftColumn + 1, rightRow - leftRow + 1),
                    ' '
            )

            backgroundColor = previousBackgroundColor
        }
    }

    /** Draws cell in game mode */
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

    /** Creates main menu and blocks control */
    fun createMenu(gameStarter: ConsoleGameStarter) {
        screen.clear()
        val panel = Panel()
        val label = Label("Current field: random")

        val actionListBox = ActionListBox(TerminalSize(24, 2))
        val textGUI = MultiWindowTextGUI(screen)

        val window = BasicWindow()
        window.component = panel

        actionListBox.addItem("Load field from file") {
            val file = FileDialogBuilder().build().showDialog(textGUI)
            gameStarter.setFieldFileName(file.absolutePath)
            label.text = "Current field: ${file.name}"
        }
        actionListBox.addItem("Start game") {
            textGUI.removeWindow(window)
            gameStarter.start()
        }

        panel.addComponent(actionListBox)
        label.addStyle(SGR.BOLD)
        panel.addComponent(label)

        textGUI.addWindowAndWait(window)
    }

    /** Prints error message and opens main menu after OK is clicked */
    fun printErrorMessage(message: String) {
        screen.clear()
        val textGUI = MultiWindowTextGUI(screen)
        MessageDialog.showMessageDialog(
            textGUI, "Error", message, MessageDialogButton.OK)
        MainMenu(this).start()
    }

    /** Refreshes screen showing updates */
    fun refreshScreen() {
        screen.refresh()
        screen.doResizeIfNecessary()
    }

    /** Cleares screen before game */
    fun prepareGame() {
        screen.clear()
        refreshScreen()
    }
}

/** Parameters for drawing cell on a console */
class DrawingParameters(
        val symbol: Char,
        val backgroundColor: TextColor = TextColor.ANSI.DEFAULT,
        val foregroundColor: TextColor = TextColor.ANSI.DEFAULT
)

class TextParameters(
        val text: String,
        val backgroundColor: TextColor = TextColor.ANSI.DEFAULT,
        val foregroundColor: TextColor = TextColor.ANSI.DEFAULT
)
