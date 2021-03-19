package cz.than.composetest.logic

fun reducer(action: Action, state: Int) = when (action) {
    Action.Decrease -> state - 1
    Action.Increase -> state + 1
}