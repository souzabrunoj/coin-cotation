package br.com.souzabrunoj.dataremote.response

import com.google.gson.annotations.SerializedName

data class CoinDetailsResponse(
    @SerializedName("categories")
    val categories: List<String>?,
    @SerializedName("description")
    val description: DescriptionResponse?,
    @SerializedName("genesis_date")
    val genesisDate: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: ImageResponse?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("links")
    val links: LinksResponse?,
    @SerializedName("market_data")
    val marketData: MarketDataResponse?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?,
)

data class DescriptionResponse(
    @SerializedName("en")
    val en: String?,
)


data class ImageResponse(
    @SerializedName("large")
    val large: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("thumb")
    val thumb: String?,
)

data class LinksResponse(
    @SerializedName("homepage")
    val homepage: List<String>?,
)

data class MarketDataResponse(
    @SerializedName("current_price")
    val currentPrice: CurrentPriceResponse?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double?,
    @SerializedName("sparkline_7d")
    val sparkline7d: Sparkline7dResponse?
)

data class CurrentPriceResponse(
    @SerializedName("eur")
    val eur: Double?,
)

data class Sparkline7dResponse(
    @SerializedName("price")
    val price: List<Double>?,
)



