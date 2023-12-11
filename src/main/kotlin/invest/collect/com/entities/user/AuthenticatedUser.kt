package invest.collect.com.entities.user

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticatedUser(
    val id: Int,
    val name: String
)

