package com.techgeni.walletpage.data.remote.getAllCards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCardModel(
    val id : Int,
    val number : String,
    @SerialName("expire_date")
    val expireDate : String,
    @SerialName("user_id")
    val userId : Int
)
