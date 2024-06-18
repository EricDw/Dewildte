import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeIcon(
	modifier: Modifier = Modifier,
	contentDescription: String? = null,
) {
	Icon(
		modifier = modifier,
		imageVector = Icons.Default.Home,
		contentDescription = contentDescription,
	)
}