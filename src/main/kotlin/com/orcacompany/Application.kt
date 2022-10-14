package com.orcacompany

import com.orcacompany.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        DataBaseFactory.init()
        configureSerialization()
        configureTemplating()
        configureRouting()

        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader("MyCustomHeader")
            allowHeader(HttpHeaders.ContentType)
            anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        }

    }.start(wait = true)
}
