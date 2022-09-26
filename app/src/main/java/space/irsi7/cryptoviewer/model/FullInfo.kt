package space.irsi7.cryptoviewer.model

import java.util.*
import kotlin.math.roundToInt

data class FullInfo(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: String,
    val market_cap: Double,
    val market_cap_rank: Double,
    val fully_diluted_valuation: Double,
    val total_volume: Double,
    val high_24h: Double,
    val low_24h: Double,
    val price_change_24h: String,
    val price_change_percentage_24h: Double,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val circulating_supply: Double,
    val total_supply: Double,
    val max_supply: Double,
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: Date,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: Date,
    val last_updated: Date,
    val price_change_percentage_24h_in_currency: Double
) {
    fun toToken(): Token{
        return Token(id, name, symbol, image)
    }
    fun toValue(): Value{
        return Value(current_price, "${(price_change_percentage_24h * 100.0).roundToInt() / 100.0}%")
    }
}