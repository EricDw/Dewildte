package presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import design.space.VerticalSpacer100
import design.space.VerticalSpacer200
import design.space.spacing
import design.text.Markdown
import dewildte.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.extensions.composeFold

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun HomeScreen(
    state: HomeScreenState = HomeScreenState(),
    modifier: Modifier = Modifier,
) {
    val (greeting) = state

    val contentModifier = modifier
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

        greeting.composeFold({}) {
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