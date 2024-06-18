package design.space

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSpacer025() {
    Spacer(
        modifier = Modifier.width(
            MaterialTheme.spacing.spacing025.dp
        )
    )
}

@Composable
fun HorizontalSpacer100() {
    Spacer(
        modifier = Modifier.width(
            MaterialTheme.spacing.spacing100.dp
        )
    )
}

@Composable
fun VerticalSpacer025() {
    Spacer(
        modifier = Modifier.height(
            MaterialTheme.spacing.spacing025.dp
        )
    )
}

@Composable
fun VerticalSpacer100() {
    Spacer(
        modifier = Modifier.height(
            MaterialTheme.spacing.spacing100.dp
        )
    )
}

@Composable
fun VerticalSpacer200() {
    Spacer(
        modifier = Modifier.height(
            MaterialTheme.spacing.spacing200.dp
        )
    )
}
