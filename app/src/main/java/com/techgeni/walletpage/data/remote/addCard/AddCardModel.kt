package com.techgeni.walletpage.data.remote.addCard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCardModel(

    val number : String,
    @SerialName("expire_date")
    val expireDate : String
)
