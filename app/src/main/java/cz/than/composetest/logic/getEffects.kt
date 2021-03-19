package cz.than.composetest.logic

import arrow.core.Either
import cz.than.composetest.mvi.Dispatch

suspend fun getEffects(
    action: Action,
    state: Int,
    dispatch: Dispatch<Action>,
    dependencies: Dependencies,
): Either.Right<Unit> = when (action) {
    else -> Either.Right(Unit)
}

data class Dependencies(
    val a: Int,
    val b: String,
)
