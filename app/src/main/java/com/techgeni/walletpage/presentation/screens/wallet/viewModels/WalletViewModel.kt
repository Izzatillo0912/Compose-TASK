package com.techgeni.walletpage.presentation.screens.wallet.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel
import com.techgeni.walletpage.domain.putPayMethod.PutPayMethodUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletUseCase: WalletUseCase,
    private val putPayMethodUseCase : PutPayMethodUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow<ActionResultState>(ActionResultState.Init)
    val walletState: StateFlow<ActionResultState> = _walletState

    private val _walletInfo = MutableStateFlow<WalletResponseModel?>(null)
    val walletInfo : StateFlow<WalletResponseModel?> = _walletInfo


    fun getWallet(retryRequest: String? = null) {

        if (retryRequest.isNullOrEmpty() || retryRequest == "getWallet") {
            viewModelScope.launch {
                _walletState.value = ActionResultState.Loading

                when(val result = walletUseCase.getWallet()) {
                    is RemoteResult.Error -> {
                        _walletState.value = ActionResultState.Error(
                            message = result.error,
                            errorType = if (result.error == "Connection Error") 1000 else 0,
                            retryApi = "getWallet"
                        )
                    }
                    is RemoteResult.Success -> {
                        _walletState.value = ActionResultState.Success(message = "")
                        _walletInfo.value = result.data
                    }
                }
            }
        }
    }

    fun putPayment(activeMethod : String, cardId : Int) {


            viewModelScope.launch {
                _walletState.value = ActionResultState.Loading

                when(val result = putPayMethodUseCase.putPayMethod(activeMethod, cardId)) {
                    is RemoteResult.Error -> {
                        _walletState.value = ActionResultState.Error(
                            message = result.error,
                            errorType = if (result.error == "Connection Error") 1000 else 0,
                            retryApi = "putPayMethod"
                        )
                    }
                    is RemoteResult.Success -> {
                        _walletState.value = ActionResultState.Success(message = "")
                        _walletInfo.value = result.data
                    }
                }
            }

    }

    fun hideDialog() : Boolean {
        return if(
            walletState.value is ActionResultState.Init ||
            walletState.value is ActionResultState.Error ||
            walletState.value is ActionResultState.Success
        ) {
            _walletState.value = ActionResultState.Init
            true
        } else { false }
    }
}