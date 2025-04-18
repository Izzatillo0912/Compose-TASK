package com.techgeni.walletpage.presentation.screens.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.domain.getWallet.WalletUseCase
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import kotlinx.coroutines.flow.MutableStateFlow
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val walletUseCase: WalletUseCase
) : ViewModel() {

    private val _walletState = MutableStateFlow<ActionResultState>(ActionResultState.Init)
    val walletState: StateFlow<ActionResultState> = _walletState
    private val _actionResultDialogShow = MutableStateFlow(false)
    val actionResultDialogShow: StateFlow<Boolean> = _actionResultDialogShow
    private var walletJob: Job? = null

    fun getWallet() {

        if (walletJob?.isActive == true) return

        walletJob = viewModelScope.launch {
            _walletState.value = ActionResultState.Loading
            _actionResultDialogShow.value = true

            when(val result = walletUseCase.getWallet()) {
                is RemoteResult.Error -> {
                    _walletState.value = ActionResultState.Error(
                        message = result.error,
                        errorType = if (result.error == "Time out error") 1000 else 0
                    )
                }
                is RemoteResult.Success -> {
                    _walletState.value = ActionResultState.Success(
                        message = "",
                        data = result.data
                    )
                    _actionResultDialogShow.value = false
                }
            }
        }
    }

    fun hideDialog() {
        _actionResultDialogShow.value = false
    }

    fun cancelWalletJob() {
        walletJob?.cancel()
        walletJob = null
    }
}