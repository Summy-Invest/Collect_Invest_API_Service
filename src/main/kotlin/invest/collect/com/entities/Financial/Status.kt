package invest.collect.com.entities.Financial

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val id: Long,
    val status: String
)