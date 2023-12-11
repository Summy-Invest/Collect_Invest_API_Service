package invest.collect.com.entities.collectible

import kotlinx.serialization.Serializable

@Serializable
data class CollectibleItem(
    val id: Long,
    val name: String,
    val description: String,
    val category: String,
    val photoUrl: String,
    val currentPrice: Double,
    var availableShares: Int
)