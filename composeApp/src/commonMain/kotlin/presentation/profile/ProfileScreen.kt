package presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.profile_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.posts.Post

@Composable
@Preview
fun ProfileScreen() {
	val contentModifier = Modifier
		.fillMaxSize()

	Post(
		modifier = contentModifier
	) {

		composable {

			val imageResource = Res.drawable.profile_image

			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {

				Image(
					painter = painterResource(imageResource),
					contentDescription = null,
					modifier = Modifier.clip(CircleShape)
				)
			}

		}

		paragraph {
			"""_Name:_ Eric De Wildt
			|_Location:_ Ontario, Canada
			|_LinkedIn:_ https://www.linkedin.com/in/eric-de-wildt
			|_E-Mail:_ dewildte@gmail.com
			""".trimMargin()
		}

		paragraph {
			"""I live for love and spend a lot of my time serving my friends and family.
			|When I am not serving others or outside I am crafting software.
			|I deeply enjoy sitting at my desk creating things with Kotlin.
			|For example, this website is built using Kotlin.
			""".trimMargin()
		}
	}

}