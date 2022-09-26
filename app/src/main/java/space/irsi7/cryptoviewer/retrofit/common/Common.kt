package space.irsi7.cryptoviewer.retrofit.common
import space.irsi7.cryptoviewer.retrofit.RetrofitClient
import space.irsi7.cryptoviewer.retrofit.`interface`.CoinGeckoAPI

object Common {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"
    val getAPI: CoinGeckoAPI
        get() = RetrofitClient.getClient(BASE_URL).create(CoinGeckoAPI::class.java)
}