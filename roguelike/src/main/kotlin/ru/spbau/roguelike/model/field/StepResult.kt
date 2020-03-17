package ru.spbau.roguelike.model.field

/** Result of turn returned in `onStep` function */
enum class StepResult {
    /** Means that character can step on cell he tried */
    STEP_SHOULD_BE_DONE,
    /** Means that character cannot step on cell he tried */
    STEP_SHOULD_BE_CANCELLED
}