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
    var selected = MutableLiveData<String>(null)
    var isDownloadingRe = MutableLiveData<Boolean?>(null)
    var isDownloadingList = MutableLiveData<Boolean?>(null)
    var isDownloadingCoin = MutableLiveData<Boolean?>(null)
    var isFailList = false
    var isFailCoin = false
    var isFailRe = false

    // TODO: Implement the ViewModel
    fun getCoinList() {
        isDownloadingList.postValue(true)
        isFailList = false
        service.getUSD().enqueue(object : Callback<List<FullInfo>> {

            override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                isFailList = true
                isDownloadingList.postValue(false)
            }

            override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                if (response.isSuccessful) {
                    tokenData.postValue(response.body()?.map { it.toToken() })
                    valueUSD.postValue(response.body()?.map { it.toValue() })
                    service.getEUR().enqueue(object : Callback<List<FullInfo>> {
                        override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                            isFailList = true
                            isDownloadingList.postValue(false)
                        }

                        override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                            if (response.isSuccessful) {
                                valueEUR.postValue(response.body()?.map { it.toValue() })
                                isDownloadingList.postValue(false)
                            } else {
                                isFailList = true
                                isDownloadingList.postValue(false)
                            }
                        }
                    })
                } else {
                    isFailList = true
                    isDownloadingList.postValue(false)
                }
            }
        })
    }

    fun getNewCoinList() {

        isDownloadingRe.value = true
        isFailRe = false
        service.getUSD().enqueue(object : Callback<List<FullInfo>> {

            override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                isFailRe = true
                isDownloadingRe.value = false
            }

            override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                if (response.isSuccessful) {
                    tokenData.postValue(response.body()?.map { it.toToken() })
                    valueUSD.postValue(response.body()?.map { it.toValue() })
                    service.getEUR().enqueue(object : Callback<List<FullInfo>> {
                        override fun onFailure(call: Call<List<FullInfo>>, t: Throwable) {
                            isFailRe = true
                            isDownloadingRe.value = false
                        }

                        override fun onResponse(call: Call<List<FullInfo>>, response: Response<List<FullInfo>>) {
                            if (response.isSuccessful) {
                                valueEUR.postValue(response.body()?.map { it.toValue() })
                            } else {
                                isFailRe = true
                            }
                            isDownloadingRe.value = false
                        }
                    })
                } else {
                    isFailRe = true
                    isDownloadingRe.value = false
                }
            }
        })
    }

    fun getCoinInfo(ID: String) {
        selected.postValue(ID)
        isDownloadingCoin.postValue(true)
        service.getCoin(ID).enqueue(object : Callback<TokenInfo> {

            override fun onFailure(call: Call<TokenInfo>, t: Throwable) {
                isFailCoin = true
                isDownloadingCoin.postValue(false)
            }

            override fun onResponse(call: Call<TokenInfo>, response: Response<TokenInfo>) {
                if (response.isSuccessful) {
                    tokenInfo.postValue(response.body())
                    isDownloadingCoin.postValue(false)
                } else {
                    isFailCoin = true
                    isDownloadingCoin.postValue(false)
                }
            }
        })
    }
}
