package ru.spbau.roguelike.model.logic

/**
 * Interface for character-type dependent logic that is done
 * when epoch of turns is finished.
 *
 * I.e. refreshing player's ui.
 */
interface AfterTurnLogic {
    /**
     * Refresh UI. Makes sense only for players,
     * for monsters implementation of this method should do nothing.
     */
    fun refreshPlayerUI()
}