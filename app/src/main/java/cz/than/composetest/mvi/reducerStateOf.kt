package cz.than.composetest.mvi

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import arrow.core.handleError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <ACTION, STATE, ERROR> reducerStateOf(
    initialValue: STATE,
    reducer: Reducer<ACTION, STATE>,
    getEffect: GetEffect<ACTION, STATE, ERROR>? = null,
    coroutineScope: CoroutineScope,
    logAction: suspend (ACTION) -> Unit = { Log.d("ACTION", it.toString()) },
    logError: suspend (ERROR) -> Unit = { Log.e("ERROR", it.toString()) },
): Pair<State<STATE>, Dispatch<ACTION>> {
    val state = mutableStateOf(initialValue)

    fun dispatch(action: ACTION) {
        val newState = reducer(action, state.value)
        state.value = newState

        coroutineScope.launch {
            logAction(action)
        }

        if (getEffect != null)
            coroutineScope.launch {
                getEffect(action, newState, ::dispatch)
                    .handleError { logError(it) }
            }
    }

    return state to ::dispatch
}
