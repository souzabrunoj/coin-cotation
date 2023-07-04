package br.com.souzabrunoj.dataremote.response.mappers

import br.com.souzabrunoj.dataremote.response.CoinItemResponse
import br.com.souzabrunoj.dataremote.response.RoiResponse
import br.com.souzabrunoj.dataremote.response.SparklineResponse
import br.com.souzabrunoj.domain.model.Coin
import br.com.souzabrunoj.domain.model.Roi
import br.com.souzabrunoj.domain.model.Sparkline

fun CoinItemResponse.toDomain() = Coin(
    ath = ath ?: 0.0,
    athChangePercentage = athChangePercentage ?: 0.0,
    athDate = athDate.orEmpty(),
    atl = atl ?: 0.0,
    atlChangePercentage = atlChangePercentage ?: 0.0,
    atlDate = atlDate.orEmpty(),
    circulatingSupply = circulatingSupply ?: 0.0,
    currentPrice = currentPrice ?: 0.0,
    fullyDilutedValuation = fullyDilutedValuation ?: 0L,
    high24h = high24h ?: 0.0,
    id = id.orEmpty(),
    image = image.orEmpty(),
    lastUpdated = lastUpdated.orEmpty(),
    low24h = low24h ?: 0.0,
    marketCap = marketCap ?: 0L,
    marketCapChange24h = marketCapChange24h ?: 0.0,
    marketCapChangePercentage24h = marketCapChangePercentage24h ?: 0.0,
    marketCapRank = marketCapRank ?: 0,
    maxSupply = maxSupply ?: 0.0,
    name = name.orEmpty(),
    priceChange24h = priceChange24h ?: 0.0,
    priceChangePercentage24h = priceChangePercentage24h ?: 0.0,
    roi = this.roi.toDomain(),
    sparkline = this.sparkline.toDomain(),
    symbol = symbol.orEmpty(),
    totalSupply = totalSupply ?: 0.0,
    totalVolume = totalVolume ?: 0.0
)

fun RoiResponse?.toDomain() = Roi(
    currency = this?.currency.orEmpty(),
    percentage = this?.percentage ?: 0.0,
    times = this?.times ?: 0.0
)


fun SparklineResponse?.toDomain() = Sparkline(price = this?.price?.map { it?: 0.0 } ?: emptyList())