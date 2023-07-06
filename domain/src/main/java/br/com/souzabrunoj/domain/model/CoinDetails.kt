package br.com.souzabrunoj.domain.model

data class CoinDetails(
    val categories: List<String>,
    val description: Description,
    val genesisDate: String,
    val id: String,
    val image: Image,
    val lastUpdated: String,
    val links: Links,
    val marketData: MarketData,
    val name: String,
    val symbol: String,
)

data class Description(
    val en: String,
)

data class Image(
    val large: String,
    val small: String,
    val thumb: String,
)

data class Links(
    val homepage: List<String>,
)

data class MarketData(
    val currentPrice: CurrentPrice,
    val priceChangePercentage24h: Double,
    val sparkline7d: Sparkline7d
)

data class CurrentPrice(
    val eur: Double,
)

data class Sparkline7d(
    val price: List<Double>,
)
