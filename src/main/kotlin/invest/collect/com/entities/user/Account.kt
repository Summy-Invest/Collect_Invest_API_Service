package invest.collect.com.entities.user

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val email: String,
    val password: String
)
