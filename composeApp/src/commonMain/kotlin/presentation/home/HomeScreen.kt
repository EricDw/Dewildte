package presentation.home

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.space.*
import design.text.Markdown
import dewildte.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
@Preview
fun HomeScreen() {
    val contentModifier = Modifier
        .fillMaxSize()
        .padding(MaterialTheme.spacing.spacing200.dp)

    Column(
        modifier = contentModifier,
    ) {
        Text(
            text = "Eric De Wildt",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        VerticalSpacer100()

        Headline(text = "Roles:")

        VerticalSpacer100()

        val roles = listOf(
            "Human",
            "Husband",
            "Cat Owner",
            "Software Developer",
            "Woods Walker",
        )

        TagRow(tags = roles)

        VerticalSpacer200()

        Headline(text = "Skills:")

        VerticalSpacer100()

        val skills = listOf(
            "Kotlin",
            "Java",
            "Android",
            "Jetpack Compose",
            "Compose Multiplatform",
            "JUnit 4",
            "Espresso",
            "Git",
            "GitHub",
            "Jira",
        )

        TagRow(tags = skills)

        var greeting by remember {
            mutableStateOf("")
        }

        LaunchedEffect(Unit) {
            greeting = Res.readBytes("files/greeting.md").decodeToString()
        }

        greeting.takeIf { it.isNotBlank() }?.let {
            VerticalSpacer200()
            Markdown(it)
        }
    }
}

@Composable
private fun Headline(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagRow(
    tags: List<String>
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spacing100.dp)
    ) {

        tags.forEach { tag ->
            Card(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.spacing100.dp),
            ) {
                Text(
                    text = tag, style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(MaterialTheme.spacing.spacing100.dp)
                )
            }
        }
    }
}