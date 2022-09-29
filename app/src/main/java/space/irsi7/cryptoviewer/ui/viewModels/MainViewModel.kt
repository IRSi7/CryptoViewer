package space.irsi7.cryptoviewer.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import space.irsi7.cryptoviewer.model.*
import space.irsi7.cryptoviewer.retrofit.common.Common


class MainViewModel : ViewModel() {

    private var service = Common.getAPI

    var selectedToken = ""
    var currentCurrency = MutableLiveData<Currency>()
    var currentState = MutableLiveData(States.DOWNLOADING)
    var tokensList = MutableLiveData<List<Token>>()
    var tokenInfo = MutableLiveData<TokenInfo?>()
    var tokensValues = mutableMapOf<Currency, List<Value>>()


    // Функция, получающая информацию о токенах и их ценах
    fun downloadTokenList() = viewModelScope.launch {
        currentState.value = States.DOWNLOADING
        //Запрашиваем информацию в валютах из Currency
        for(i in Currency.values()){
            try{
                val response = service.getTokenInCur(i.name.lowercase())
                val result = mutableListOf<Value>()
                if (response.isNotEmpty()) {
                    response.map { it.toValue() }?.let { result.addAll(it) }
                    tokensValues[i] = result
                    tokensList.postValue(response?.map { it.toToken() })
                }
            }catch(exception:Exception){
                currentState.postValue(States.ERROR)
                break
            }
        }
        if(currentState.value != States.ERROR){
            currentCurrency.value = Currency.USD
            currentState.value = States.TOKENSLIST
        }
    }

    // Функция, перезагружающая информацию о  ценах
    suspend fun reloadTokenListAsync() = viewModelScope.async {
        //Запрашиваем информацию в валютах из Currency
        var isError = false
        for (i in Currency.values()) {
            try{
                val response = service.getTokenInCur(i.name.lowercase())
                val result = mutableListOf<Value>()
                if (response.isNotEmpty()) {
                    response.map { it.toValue() }.let { result.addAll(it) }
                    tokensValues[i] = result
            }
            }catch(exception:Exception){
                isError = true
                break
            }
        }
        return@async isError
    }

    // Функция, получающая информацию о конкретном токене и его описание
    fun downloadTokenInfo(ID: String) = viewModelScope.launch {
        try{
            val response = service.getCoin(ID)
            if (response != null) {
                tokenInfo.postValue(response)
        }
        }catch(exception:Exception){
            tokenInfo.postValue(null)
        }
        currentState.postValue(States.SELECTED)
    }
}
