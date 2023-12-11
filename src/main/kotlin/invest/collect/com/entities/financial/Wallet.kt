package invest.collect.com.entities.financial

import kotlinx.serialization.Serializable

@Serializable
data class Wallet(
    val id: Long,
    val userId: Long,
    val balance: Double,
    val status: String,
)
