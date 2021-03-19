package cz.than.composetest.mvi

import arrow.core.Either

typealias Reducer<ACTION, STATE> = (ACTION, STATE) -> STATE
typealias Dispatch<ACTION> = (ACTION) -> Unit
typealias GetEffect<ACTION, STATE, ERROR> = suspend (ACTION, STATE, Dispatch<ACTION>) -> Either<ERROR, Unit>
