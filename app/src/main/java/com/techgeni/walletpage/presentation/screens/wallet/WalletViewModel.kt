package com.techgeni.walletpage.presentation.screens.wallet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.data.remote.getWallet.WalletResponseModel
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.utils.RemoteResult
import kotlinx.coroutines.flow.MutableStateFlow
import com.techgeni.walletpage.utils.Error
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletUseCase: WalletUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow<RemoteResult<WalletResponseModel, Error>>(RemoteResult.Init)
    val walletState: StateFlow<RemoteResult<WalletResponseModel, Error>> = _walletState

    fun getWallet() {
        viewModelScope.launch {
            _walletState.value = RemoteResult.Loading
            val result = walletUseCase.getWallet()
            _walletState.value = result
        }
    }
}