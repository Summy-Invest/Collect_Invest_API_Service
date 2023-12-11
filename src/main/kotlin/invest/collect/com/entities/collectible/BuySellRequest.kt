package invest.collect.com.entities.collectible

data class BuySellRequest(
    val collectibleId: Long,
    val userId: Long,
    val sharesToBuy: Int
)
