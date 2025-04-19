package com.techgeni.walletpage.presentation.screens.addCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techgeni.walletpage.data.build.ktorClient.RemoteResult
import com.techgeni.walletpage.domain.cards.CardsUseCase
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddCardViewModel(private val carsUseCase : CardsUseCase) : ViewModel() {

    private val _addCardState = MutableStateFlow<ActionResultState>(ActionResultState.Init)
    val addCardState: StateFlow<ActionResultState> = _addCardState

    fun addNewCard(cardNumber : String, expireDate : String) {

        viewModelScope.launch {
            _addCardState.value = ActionResultState.Loading

            when(val result = carsUseCase.addNewCard(cardNumber, expireDate)) {
                is RemoteResult.Error -> {
                    _addCardState.value = ActionResultState.Error(
                        message = result.error,
                        errorType = if (result.error == "Connection Error") 1000 else 0,
                        retryApi = "addCard"
                    )
                }
                is RemoteResult.Success -> {
                    _addCardState.value = ActionResultState.Success(
                        message = "Successfully added new card :)"
                    )
                }
            }
        }
    }
}