package cz.than.composetest.logic

sealed class Action {
    object Increase : Action()
    object Decrease : Action()

}