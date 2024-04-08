package domain

import androidx.compose.runtime.Composable

sealed interface PostItem

data class TitleItem(
	val text: String,
) : PostItem

data class ParagraphItem(
	val text: String,
) : PostItem

data class ComposableItem(
	val code: @Composable () -> Unit,
) : PostItem