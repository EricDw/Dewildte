package presentation.home

import androidx.compose.runtime.*
import dewildte.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import toSome

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreenController() {
	var state by remember {
		mutableStateOf(HomeScreenState())
	}

	HomeScreen(state = state)

	LaunchedEffect(Unit) {
		try {
			val greeting = Res
				.readBytes("files/greeting.md")
				.decodeToString()
				.toSome()

			state = state.copy(
				greeting = greeting
			)
			
		} catch (exception: Throwable) {
			println(exception)
		}
	}

}