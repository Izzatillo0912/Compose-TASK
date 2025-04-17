package com.techgeni.walletpage.data.remote.getWallet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletResponseModel(
    val id: Int,
    val balance: Int,
    val phone: String,
    @SerialName("active_method") val activeMethod: String,
    @SerialName("active_card_id") val activeCardId: Int
)