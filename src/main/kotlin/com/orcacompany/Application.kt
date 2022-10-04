package com.orcacompany

import com.orcacompany.dao.sql.DataBaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.orcacompany.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        DataBaseFactory.init()
        configureSerialization()
        configureTemplating()
        configureRouting()
    }.start(wait = true)
}
