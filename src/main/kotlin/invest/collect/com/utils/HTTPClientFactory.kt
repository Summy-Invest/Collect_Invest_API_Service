package invest.collect.com.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

object HttpClientFactory {
    fun createHttpClient(): HttpClient {
        return HttpClient(CIO){
            install(ContentNegotiation) {
                json(contentType = ContentType.Any)
            }
        }
    }
}