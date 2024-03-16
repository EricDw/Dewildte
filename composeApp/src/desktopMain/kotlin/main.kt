import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import design.DewildteTheme
import presentation.DesktopApp

fun main() = application {
	Window(onCloseRequest = ::exitApplication, title = "Dewildte") {
		DewildteTheme {
			DesktopApp()
		}
	}
}