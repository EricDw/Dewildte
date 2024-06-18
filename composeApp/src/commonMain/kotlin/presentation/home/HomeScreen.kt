package presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.space.*
import design.text.Markdown
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.android_logo
import dewildte.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun HomeScreen() {
    val contentModifier = Modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.spacing200.dp)

    Column(
        modifier = contentModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Hello World!",
            style = MaterialTheme.typography.displayLarge
        )

        VerticalSpacer100()

        Text(
            text = "Brought to you by:",
            style = MaterialTheme.typography.titleLarge,
        )

        VerticalSpacer200()

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {

            Card {

                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.spacing100.dp),
                ) {

                    Image(
                        modifier = Modifier.align(Alignment.CenterHorizontally).size(100.dp),
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null
                    )

                    VerticalSpacer025()

                    Text(
                        text = "Compose Multiplatform",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

            }

        }

    }

//    Post(
//        modifier = contentModifier,
//    ) {
//        title {
//            """Hello, World!"""
//        }
//
//        paragraph {
//            """Welcome to my website!"""
//        }
//
//        paragraph {
//            """Welcome to my website!
//            |
//            |The purpose of this website is to showcase my software development skills and host my personal blog.
//			|I am proud to say that I built this website using Kotlin Multilplatform!""".trimMargin()
//        }
//
//        paragraph {
//            """Thank you so much for visiting, I hope you have a fun time poking around."""
//        }
//
//        paragraph {
//            """_WARNING:_
//				|This place is a bit of a proving grounds for me and as such it is often using experimental technology.
//				|I apologise in advance for any bugs or glitches caused by any instability you find here."""
//        }
//    }
}