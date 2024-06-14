package com.publicmethod.dewildte

import SERVER_PORT
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(
		factory = Netty,
		port = SERVER_PORT,
		host = "0.0.0.0",
		module = Application::module
	).start(
        wait = true
    )
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Ktor: Response}")
        }
    }
}