package space.irsi7.cryptoviewer.retrofit.common
import space.irsi7.cryptoviewer.retrofit.`interface`.UsdRetrofitService
import space.irsi7.cryptoviewer.retrofit.RetrofitClient
import space.irsi7.cryptoviewer.retrofit.`interface`.EurRetrofitService

object Common {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/coins/"
    val USDRetrofitService: UsdRetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(UsdRetrofitService::class.java)
    val EURRetrofitService: EurRetrofitService
        get() = RetrofitClient.getClient(BASE_URL).create(EurRetrofitService::class.java)
}