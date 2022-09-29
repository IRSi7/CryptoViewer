package space.irsi7.cryptoviewer.retrofit.`interface`
import space.irsi7.cryptoviewer.model.Token
import retrofit2.Call
import retrofit2.http.*
import space.irsi7.cryptoviewer.model.Currency
import space.irsi7.cryptoviewer.model.FullInfo
import space.irsi7.cryptoviewer.model.TokenInfo

interface CoinGeckoAPI {
    @GET("coins/markets?order=market_cap_desc&per_page=30&page=1&sparkline=false&price_change_percentage=24h")
    suspend fun getTokenInCur(@Query("vs_currency") currency: String): List<FullInfo>
    @GET("coins/{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false")
    suspend fun getCoin(@Path("id") ID: String): TokenInfo?
}