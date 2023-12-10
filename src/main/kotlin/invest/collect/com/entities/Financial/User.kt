package invest.collect.com.entities.Financial

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val name: String,
    val email: String,
    val password: String
)
