package presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.post
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.posts.Post
import presentation.posts.PostState

@Composable
@Preview
fun HomeScreen() {
	val contentModifier = Modifier
		.fillMaxSize()

	val post = post {
		title {
			"""Hello, World!"""
		}

		paragraph {
			"Welcome to my website!"
		}

		paragraph {
			"""I am proud to say that I built this website using Kotlin Multilplatform 😎
				|The purpose of this website is to showcase my software development skills and host my personal blog.""".trimMargin()
		}

		paragraph {
			"""Thank you so much for visiting, I hope you have a fun time poking around."""
		}

		paragraph {
			"""WARNING:
				|This place is a bit of a proving grounds for me and as such it is often using experiemental technology.
				|I apologise in advance for any bugs or glitches caused by any instability you find here.""".trimMargin()
		}
	}

	val postState = PostState(
		post = post
	)

	Post(
		state = postState,
		modifier = contentModifier
	)
}