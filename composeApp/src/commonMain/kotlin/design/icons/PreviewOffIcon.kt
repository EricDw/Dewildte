import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HideSource
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dewildte.composeapp.generated.resources.Res
import dewildte.composeapp.generated.resources.compose_multiplatform
import dewildte.composeapp.generated.resources.preview_off_24dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun PreviewOffIcon(
	modifier: Modifier = Modifier,
	contentDescription: String? = null,
) {
	Icon(
		modifier = modifier,
		painter = painterResource(Res.drawable.preview_off_24dp),
		contentDescription = contentDescription,
	)
}