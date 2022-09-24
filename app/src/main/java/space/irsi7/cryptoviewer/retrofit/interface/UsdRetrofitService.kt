package space.irsi7.cryptoviewer.retrofit.`interface`
import space.irsi7.cryptoviewer.model.Token
import retrofit2.Call
import retrofit2.http.*
import space.irsi7.cryptoviewer.model.FullInfo

interface UsdRetrofitService {
    @GET("markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false&price_change_percentage=24h")
    fun getInfoList(): Call<List<FullInfo>>
}