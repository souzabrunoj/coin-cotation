package br.com.souzabrunoj.dataremote.response.mappers

import br.com.souzabrunoj.dataremote.response.CoinDetailsResponse
import br.com.souzabrunoj.dataremote.response.CurrentPriceResponse
import br.com.souzabrunoj.dataremote.response.DescriptionResponse
import br.com.souzabrunoj.dataremote.response.ImageResponse
import br.com.souzabrunoj.dataremote.response.LinksResponse
import br.com.souzabrunoj.dataremote.response.MarketDataResponse
import br.com.souzabrunoj.dataremote.response.Sparkline7dResponse
import br.com.souzabrunoj.domain.model.CoinDetails
import br.com.souzabrunoj.domain.model.CurrentPrice
import br.com.souzabrunoj.domain.model.Description
import br.com.souzabrunoj.domain.model.Image
import br.com.souzabrunoj.domain.model.Links
import br.com.souzabrunoj.domain.model.MarketData
import br.com.souzabrunoj.domain.model.Sparkline7d

fun CoinDetailsResponse.toDomain() = CoinDetails(
    categories = categories ?: emptyList(),
    description = description.toDomain(),
    genesisDate = genesisDate.orEmpty(),
    id = id.orEmpty(),
    image = image.toDomain(),
    lastUpdated = lastUpdated.orEmpty(),
    links = links.toDomain(),
    marketData = marketData.toDomain(),
    name = name.orEmpty(),
    symbol = symbol.orEmpty(),
)

fun DescriptionResponse?.toDomain() = Description(
    en = this?.en ?: ""
)

fun ImageResponse?.toDomain() = Image(
    large = this?.large ?: "",
    small = this?.small ?: "",
    thumb = this?.thumb ?: ""
)

fun LinksResponse?.toDomain() = Links(
    homepage = this?.homepage ?: emptyList()
)

fun MarketDataResponse?.toDomain() = MarketData(
    currentPrice = this?.currentPrice.toDomain(),
    priceChangePercentage24h = this?.priceChangePercentage24h ?: 0.0,
    sparkline7d = this?.sparkline7d.toDomain()
)

fun CurrentPriceResponse?.toDomain() = CurrentPrice(
    eur = this?.eur ?: 0.0
)

fun Sparkline7dResponse?.toDomain() = Sparkline7d(
    price = this?.price ?: emptyList()
)