package domain

import androidx.compose.runtime.Composable

data class Post(
	val id: Id,
	val items: List<PostItem> = emptyList(),
) {
	
	data class Id(val value: String) {
		init {
			require(value.isNotBlank())
		}
	}
	
	interface Builder {
		
		fun title(
			block: () -> String
		)

		fun paragraph(
			block: () -> String
		)

		fun composable(
			block: @Composable () -> Unit
		)
	}
}