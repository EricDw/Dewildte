import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ExampleJUnitTest {
	@get:Rule
	val rule = createComposeRule()

	@Test
	fun myTest(){
		// Declares a mock UI to demonstrate API calls
		//
		// Replace with your own declarations to test the code in your project
		rule.setContent {
			var text by remember { mutableStateOf("Jello") }

			Text(
				text = text,
				modifier = Modifier.testTag("text")
			)
			Button(
				onClick = { text = "Compose" },
				modifier = Modifier.testTag("button")
			) {
				Text("Click me")
			}
		}

		// Tests the declared UI with assertions and actions of the JUnit-based testing API
		rule.run {
			onNodeWithTag("text").assertTextEquals("Hello")
			onNodeWithTag("button").performClick()
			onNodeWithTag("text").assertTextEquals("Compose")
		}
	}
}