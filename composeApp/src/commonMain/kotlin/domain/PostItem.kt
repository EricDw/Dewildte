package domain

import androidx.compose.runtime.Composable

sealed interface PostItem

data class TitleItem(
	val rawText: String,
) : PostItem {
	val text: String
		get() = rawText.trimMargin()
}

data class ParagraphItem(
	val rawText: String,
) : PostItem {
	val text: String
		get() = rawText.trimMargin()
}

data class ComposableItem(
	val code: @Composable () -> Unit,
) : PostItem