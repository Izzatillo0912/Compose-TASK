package com.techgeni.walletpage.presentation.screens.wallet.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.domain.cards.CardsUseCase
import com.techgeni.walletpage.domain.cards.MyCardModel
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val cardsUseCase: CardsUseCase
) : ViewModel() {

    private val _cardsState = MutableStateFlow<ActionResultState>(ActionResultState.Init)
    val cardsState: StateFlow<ActionResultState> = _cardsState

    private val _cards = MutableStateFlow<List<MyCardModel>>(emptyList())
    val cards: StateFlow<List<MyCardModel>> = _cards


    fun getCards(retryRequest: String? = null) {

        if (retryRequest.isNullOrEmpty() || retryRequest == "getWallet") {
            viewModelScope.launch {
                _cardsState.value = ActionResultState.Loading

                when(val result = cardsUseCase.getAllCards()) {
                    is RemoteResult.Error -> {
                        _cardsState.value = ActionResultState.Error(
                            message = result.error,
                            errorType = if (result.error == "Connection Error") 1000 else 0,
                            retryApi = "getCards"
                        )
                    }
                    is RemoteResult.Success -> {
                        _cardsState.value = ActionResultState.Success(message = "")
                        _cards.value = result.data
                    }
                }
            }
        }
    }

    fun selectCard(index: Int) {
        _cards.value = _cards.value.mapIndexed { i, card ->
            card.copy(isSelected = i == index)
        }
    }

    fun hideDialog() : Boolean {
        return if(
            cardsState.value is ActionResultState.Init ||
            cardsState.value is ActionResultState.Error ||
            cardsState.value is ActionResultState.Success
        ) {
            _cardsState.value = ActionResultState.Init
            true
        } else { false }
    }
}