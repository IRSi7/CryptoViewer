package space.irsi7.cryptoviewer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.irsi7.cryptoviewer.model.FullInfo
import space.irsi7.cryptoviewer.model.Token
import space.irsi7.cryptoviewer.model.TokenInfo
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.retrofit.common.Common

class MainViewModel : ViewModel() {

    private var service = Common.getAPI

    var chosenVal = MutableLiveData(0)
    var tokenData = MutableLiveData<List<Token>>()
    var tokenInfo = MutableLiveData<TokenInfo>()
    var valueUSD = MutableLiveData<List<Value>>()
    var valueEUR = MutableLiveData<List<Value>>()
    var isSelected = MutableLiveData(false)
    var isDownloading = MutableLiveData(false)
    var isFail = MutableLiveData(false)

    // TODO: Implement the ViewModel
    fun getCoinList() {
        isDownloading.postValue(true)
//        dialog.show()
        service.getUSD().enqueue(object : Callback<List<FullInfo>> {

            override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                isFail.postValue(true)
                isDownloading.postValue(false)
            }

            override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                if (response.isSuccessful) {
                    tokenData.postValue(response.body()?.map { it.toToken() })
                    valueUSD.postValue(response.body()?.map { it.toValue() })
                    service.getEUR().enqueue(object : Callback<List<FullInfo>> {
                        override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                            isFail.postValue(true)
                            isDownloading.postValue(false)
                        }

                        override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                            if (response.isSuccessful) {
                                valueEUR.postValue(response.body()?.map { it.toValue() })
                                isDownloading.postValue(false)
                            } else {
                                isFail.postValue(true)
                                isDownloading.postValue(false)
                            }
                        }
                    })
                } else {
                    isFail.postValue(true)
                    isDownloading.postValue(false)
                }
            }
        })
    }

    fun getCoinInfo(ID: String) {
        service.getCoin(ID).enqueue(object : Callback<TokenInfo> {

            override fun onFailure(call: Call<TokenInfo>, t: Throwable) {
            }

            override fun onResponse(call: Call<TokenInfo>, response: Response<TokenInfo>) {
                if (response.isSuccessful) {
                    tokenInfo.postValue(response.body())
                    isSelected.postValue(true)
                }
            }
        })
    }
}
