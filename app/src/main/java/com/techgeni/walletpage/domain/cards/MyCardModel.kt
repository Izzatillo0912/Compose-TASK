package com.techgeni.walletpage.domain.cards

data class MyCardModel(
    val id : Int,
    val number : String,
    var isSelected : Boolean = false
)
