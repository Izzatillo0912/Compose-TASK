package com.techgeni.walletpage.presentation.dialogs.actionResult

sealed interface ActionResultState {
    data object Loading : ActionResultState
    data object Init : ActionResultState
    data class Success(val message: String) : ActionResultState
    data class Error(val message: String, val errorType : Int, val retryApi : String) : ActionResultState

}