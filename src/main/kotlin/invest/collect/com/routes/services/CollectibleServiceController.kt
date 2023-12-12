package invest.collect.com.routes.services

import invest.collect.com.entities.collectible.*
import invest.collect.com.utils.HttpClientSingleton
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val collectibleUrl = "http://localhost:3937"

fun Route.collectibleRoutes(){
    post("/buy"){
        try {


            val buyRequest = call.receive<BuySellRequest>()
            val client = HttpClientSingleton.client
            val response: HttpResponse = client.post("$collectibleUrl/buyCollectible")
            {
                contentType(ContentType.Application.Json)
                setBody(buyRequest)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, "Покупка успешно выполнена")
                }

                else -> {
                    call.respond(response.status, response.body<String>())
                }
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest, e.toString())
        }
    }

    post("/sell"){
        try {

            val sellRequest = call.receive<BuySellRequest>()
            val client = HttpClientSingleton.client
            val response: HttpResponse = client.post("$collectibleUrl/sellCollectible")
            {
                contentType(ContentType.Application.Json)
                setBody(sellRequest)
            }
            when (response.status) {
                HttpStatusCode.OK -> {
                    call.respond(HttpStatusCode.OK, "Продажа успешно выполнена")
                }

                else -> {
                    call.respond(response.status, response.body<String>())
                }
            }
        }catch (e: Throwable){
            call.respond(HttpStatusCode.BadRequest, e.toString())
        }
    }

    get("/getCollectible/{id}"){
        val id = call.parameters["id"]!!.toInt()
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.get("$collectibleUrl/getCollectibleById/$id")
        when (response.status) {
            HttpStatusCode.OK -> {
                call.respond(HttpStatusCode.OK, response.body<CollectibleItem>())
            }

            else -> {
                call.respond(response.status, response.body<String>())
            }
        }
    }

    get("/getAllCollectibles"){
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.get("$collectibleUrl/getAllCollectibles")
        when (response.status) {
            HttpStatusCode.OK -> {
                call.respond(HttpStatusCode.OK, response.body<List<CollectibleItem>>())
            }

            else -> {
                call.respond(response.status, response.body<String>())
            }
        }
    }

    get("/getAllCollectibles/{userId}") {
        val userId = call.parameters["userId"]!!.toLong()
        val client = HttpClientSingleton.client
        val response: HttpResponse = client.get("$collectibleUrl/getAllUserCollectibles/$userId")
        when (response.status) {
            HttpStatusCode.OK -> {
                call.respond(HttpStatusCode.OK, response.body<List<CollectibleItem>>())
            }

            else -> {
                call.respond(response.status, response.body<String>())
            }
        }
    }
}
