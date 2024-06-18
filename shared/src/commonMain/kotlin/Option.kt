sealed class Option<T> {
    class None<T> : Option<T>()

    data class Some<T>(val value: T) : Option<T>()
}

fun <T> Option<T>.fold(
    onNone: () -> Unit,
    onSome: (value: T) -> Unit,
) {
    when (this) {
        is Option.None -> onNone()
        is Option.Some -> onSome(value)
    }
}

fun <T> some(value: T): Option.Some<T> {
    return Option.Some(value = value)
}

fun <T> T.toSome(): Option.Some<T> {
    return some(value = this)
}

fun <T> none(): Option.None<T> {
    return Option.None()
}

private fun main() {
    val greeting: Option<String> = "Hello World".toSome()

    println(greeting)

    greeting.fold(::println, ::println)
}