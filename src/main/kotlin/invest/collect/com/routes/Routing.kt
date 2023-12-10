package invest.collect.com.routes

import invest.collect.com.routes.services.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("userService"){
            userRoutes()
        }

        route("financialService"){
            financialRoutes()
        }

        route("collectibleService"){
            collectibleRoutes()
        }

    }
}
