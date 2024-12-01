package invest.collect.com.routes.services

import invest.collect.com.entities.Message
import invest.collect.com.entities.financial.Wallet
import invest.collect.com.utils.HttpClientSingleton
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val financialUrl = "http://financial-service:7777"
fun Route.financialRoutes() {
    put("/topUp/{userId}/{amount}") {
        val userId: Long = call.parameters["userId"]!!.toLong()
        val amount: Double = call.parameters["amount"]!!.toDouble()
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.put("$financialUrl/topUp/$userId/$amount")
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

    get("/getWallet/{userId}") {
        val userId: Long = call.parameters["userId"]!!.toLong()
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.get("$financialUrl/getWallet/$userId")
        when (response.status) {
            HttpStatusCode.OK -> {
                call.respond(HttpStatusCode.OK, response.body<Wallet>())
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