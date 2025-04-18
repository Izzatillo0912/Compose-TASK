package com.techgeni.walletpage.presentation.dialogs.actionResult

sealed interface ActionResultState {
    data object Loading : ActionResultState
    data object Init : ActionResultState
    data class Success<T>(val message: String, val data : T) : ActionResultState
    data class Error(val message: String, val errorType : Int) : ActionResultState

}