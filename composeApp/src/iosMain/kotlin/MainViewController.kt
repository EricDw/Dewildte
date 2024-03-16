import androidx.compose.ui.window.ComposeUIViewController
import design.DewildteTheme
import presentation.MobileApp

fun MainViewController() = ComposeUIViewController {
	DewildteTheme {
		MobileApp()
	}
}