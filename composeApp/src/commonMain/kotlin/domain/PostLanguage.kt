package domain

fun post(intializer: Post.Builder.() -> Unit): Post {
	return RealPostBuilder()
		.apply(intializer)
		.build()
}