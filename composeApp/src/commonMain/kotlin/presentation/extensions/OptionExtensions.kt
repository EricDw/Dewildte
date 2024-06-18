package presentation.extensions

import Option
import androidx.compose.runtime.Composable

@Composable
fun <T> Option<T>.composeFold(
    onNone: @Composable () -> Unit,
    onSome: @Composable (value: T) -> Unit,
) {
    when (this) {
        is Option.None -> onNone()
        is Option.Some -> onSome(value)
    }
}