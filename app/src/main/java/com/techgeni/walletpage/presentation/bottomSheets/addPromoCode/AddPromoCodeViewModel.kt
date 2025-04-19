package com.techgeni.walletpage.presentation.bottomSheets.addPromoCode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.domain.addPromoCode.AddPromoCodeUseCase
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddPromoCodeViewModel(private val addPromoCodeUseCase: AddPromoCodeUseCase) : ViewModel() {

    private val _addPromoCodeState = MutableStateFlow<ActionResultState>(ActionResultState.Init)
    val addPromoCodeState: StateFlow<ActionResultState> = _addPromoCodeState

    fun addPromoCode(promoCode: String) {

        viewModelScope.launch {
            _addPromoCodeState.value = ActionResultState.Loading

            when(val result = addPromoCodeUseCase.addPromoCode(promoCode)) {
                is RemoteResult.Error -> {
                    _addPromoCodeState.value = ActionResultState.Error(
                        message = result.error,
                        errorType = if (result.error == "Connection Error") 1000 else 0,
                        retryApi = "addPromoCode"
                    )
                }
                is RemoteResult.Success -> {
                    _addPromoCodeState.value = ActionResultState.Success(
                        message = result.data
                    )
                }
            }
        }
    }
}