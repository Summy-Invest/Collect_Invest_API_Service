package invest.collect.com.routes

import invest.collect.com.routes.services.financialRoutes
import invest.collect.com.routes.services.userRoutes
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

    }
}
