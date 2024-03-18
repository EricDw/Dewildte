package domain

data class Name(
	val value: String
) {
	init {
		if (value.isBlank()) {
			throw IllegalStateException("Name can not be blank")
		}
	}
	
	sealed class Error : Throwable() {
		data object Blank: Error()
	}
}