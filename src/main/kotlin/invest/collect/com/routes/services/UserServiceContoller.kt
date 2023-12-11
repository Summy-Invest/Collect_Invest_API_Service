package invest.collect.com.routes.services
import invest.collect.com.entities.Message
import invest.collect.com.entities.user.AuthenticatedUser
import invest.collect.com.entities.user.User
import invest.collect.com.utils.HttpClientFactory
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
        val user = call.receive<User>()
        val client = HttpClientFactory.client
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
    }

    get("/logIn/{email}/{password}") {
        val email = call.parameters["email"]!!
        val password = call.parameters["password"]!!
        val client = HttpClientFactory.client
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
    }

}