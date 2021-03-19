package cz.than.composetest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import arrow.core.Either
import cz.than.composetest.logic.Action
import cz.than.composetest.logic.Dependencies
import cz.than.composetest.logic.getEffects
import cz.than.composetest.logic.reducer
import cz.than.composetest.mvi.reducerStateOf
import cz.than.composetest.ui.theme.ComposeTestTheme
import kotlinx.serialization.Serializable
import kotlin.reflect.KSuspendFunction4

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppContainer(Dependencies(0, ""))
                }
            }
        }
    }
}


@Composable
fun AppContainer(dependencies: Dependencies) {
    val coroutineScope = rememberCoroutineScope()
    val (stt, dispatch) = remember {
        reducerStateOf(
            initialValue = 0,
            reducer = ::reducer,
            getEffect = ::getEffects.partially4(dependencies),
            coroutineScope = coroutineScope
        )
    }
    Counter(
        value = stt.value,
        onLeftClick = { dispatch(Action.Increase) },
        onRightClick = {dispatch(Action.Decrease)})
}

@Composable
fun Counter(
    value: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    Column {
        Row {
            Text(text = "Hello world $value")
        }
        Row {
            Column {
                Button(onClick = onLeftClick) {
                    Text(text = "Left")
                }
            }
            Column {
                Button(onClick = onRightClick) {
                    Text(text = "Right")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        AppContainer(Dependencies(0, ""))
    }
}
