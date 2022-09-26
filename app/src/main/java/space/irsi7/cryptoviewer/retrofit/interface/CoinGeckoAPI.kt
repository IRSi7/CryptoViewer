package space.irsi7.cryptoviewer.retrofit.`interface`
import space.irsi7.cryptoviewer.model.Token
import retrofit2.Call
import retrofit2.http.*
import space.irsi7.cryptoviewer.model.FullInfo
import space.irsi7.cryptoviewer.model.TokenInfo

interface CoinGeckoAPI {
    @GET("coins/markets?vs_currency=eur&order=market_cap_desc&per_page=30&page=1&sparkline=false&price_change_percentage=24h")
    fun getEUR(): Call<List<FullInfo>>
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false&price_change_percentage=24h")
    fun getUSD(): Call<List<FullInfo>>
    @GET("coins/{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false")
    fun getCoin(@Path("id") ID: String): Call<TokenInfo>
}