package com.techgeni.walletpage.data.remote.putPaymentMethod

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutPayMethodModel(
    @SerialName("active_method")
    val activeMethod: String,
    @SerialName("active_card_id")
    val activeCardId: Int
)
