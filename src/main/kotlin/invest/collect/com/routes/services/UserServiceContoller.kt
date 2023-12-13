package invest.collect.com.routes.services
import invest.collect.com.entities.Message
import invest.collect.com.entities.user.AuthenticatedUser
import invest.collect.com.entities.user.User
import invest.collect.com.utils.HttpClientSingleton
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val userUrl = "http://localhost:9999"
fun Route.userRoutes(){
    post("/signUp") {
        try {
            val user = call.receive<User>()
            val client = HttpClientSingleton.client
            val response: HttpResponse = client.post("$userUrl/signUp")
            {
                contentType(ContentType.Application.Json)
                setBody(user)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, response.body<AuthenticatedUser>())
                }

                else -> {
                    call.respondText(
                        text = Json.encodeToString(response.body<Message>()),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.ServiceUnavailable
                    )
                }
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest, Message(e.toString()))
        }
    }

    get("/logIn/{email}/{password}") {
        try {
            val email = call.parameters["email"]!!
            val password = call.parameters["password"]!!
            val client = HttpClientSingleton.client
            val response: HttpResponse = client.get("$userUrl/logIn/$email/$password")
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, response.body<AuthenticatedUser>())
                }

                else -> {
                    call.respondText(
                        text = Json.encodeToString(response.body<Message>()),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.ServiceUnavailable
                    )
                }
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest, Message(e.toString()))
        }
    }

}
