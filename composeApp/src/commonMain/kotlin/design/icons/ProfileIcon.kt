import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileIcon(
	modifier: Modifier = Modifier,
	contentDescripion: String? = null,
) {
	Icon(
		modifier = modifier,
		imageVector = Icons.Default.Person,
		contentDescription = contentDescripion,
	)
}