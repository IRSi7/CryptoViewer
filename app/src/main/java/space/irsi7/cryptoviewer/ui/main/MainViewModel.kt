package space.irsi7.cryptoviewer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.irsi7.cryptoviewer.model.FullInfo
import space.irsi7.cryptoviewer.model.Token
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.retrofit.common.Common

class MainViewModel : ViewModel() {

    private var uService = Common.USDRetrofitService
    private var eService = Common.EURRetrofitService

    var chosenVal = MutableLiveData<Int>(0)
    var tokenData = MutableLiveData<List<Token>>()
    var valueUSD = MutableLiveData<List<Value>>()
    var valueEUR = MutableLiveData<List<Value>>()
    var isDownloading = MutableLiveData(false)
    var isFail = MutableLiveData(false)

    // TODO: Implement the ViewModel
    fun getTokensInfo() {
        isDownloading.postValue(true)
//        dialog.show()
        uService.getInfoList().enqueue(object : Callback<List<FullInfo>> {
            override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                isFail.postValue(true)
            }

            override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                val test = response.body()?.map { it.toToken() }
                tokenData.postValue(response.body()?.map { it.toToken() })
                valueUSD.postValue(response.body()?.map { it.toValue() })
                eService.getInfoList().enqueue(object : Callback<List<FullInfo>> {
                    override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                        if(!isFail.value!!) {
                            isFail.postValue(true)
                        }
                    }

                    override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                        val test = response.body()?.map { it.toToken() }
                        if(response.body() != null) {
                            valueEUR.postValue(response.body()?.map { it.toValue() })
                            isDownloading.postValue(false)
                        } else {
                            isFail.postValue(true)
                        }
                    }
                })
            }
        })

    }
}
