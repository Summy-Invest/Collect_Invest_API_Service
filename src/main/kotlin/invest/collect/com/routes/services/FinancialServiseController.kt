package invest.collect.com.routes.services

import invest.collect.com.entities.Financial.Status
import invest.collect.com.entities.Message
import invest.collect.com.utils.HttpClientFactory
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val url = "http://localhost:7777"
fun Route.financialRoutes() {
    put("/topUp/{userId}/{amount}") {
        val userId: Long = call.parameters["userId"]!!.toLong()
        val amount: Int = call.parameters["amount"]!!.toInt()
        HttpClientFactory.createHttpClient().use { client ->
            val response: HttpResponse = client.put("$url/topUp/$userId/$amount")
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, "$amount")
                }

                else -> {
                    call.respondText(
                        text = Json.encodeToString(response.body<Message>()),
                        contentType = ContentType.Application.Json,
                        status = response.status
                    )
                }
            }
        }
    }

    post("/buy/{userId}/{amount}") {
        val userId: Long = call.parameters["userId"]!!.toLong()
        val amount: Int = call.parameters["amount"]!!.toInt()
        HttpClientFactory.createHttpClient().use { client ->
            val response: HttpResponse = client.post("$url/buy/$userId/$amount")
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, response.body<Status>())
                }

                else -> {
                    call.respondText(
                        text = Json.encodeToString(Message("error while connecting to financial service")),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.ServiceUnavailable
                    )
                }
            }
        }
    }

    post("/sell/{userId}/{amount}") {
        val userId: Long = call.parameters["userId"]!!.toLong()
        val amount: Int = call.parameters["amount"]!!.toInt()
        HttpClientFactory.createHttpClient().use { client ->
            val response: HttpResponse = client.post("$url/sell/$userId/$amount")
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, response.body<Status>())
                }

                else -> {
                    call.respondText(
                        text = Json.encodeToString(Message("error while connecting to financial service")),
                        contentType = ContentType.Application.Json,
                        status = HttpStatusCode.ServiceUnavailable
                    )
                }
            }
        }
    }

}