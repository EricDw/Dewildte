import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MenuIcon(
	modifier: Modifier = Modifier,
	contentDescripion: String? = null,
) {
	Icon(
		modifier = modifier,
		imageVector = Icons.Default.Menu,
		contentDescription = contentDescripion,
	)
}