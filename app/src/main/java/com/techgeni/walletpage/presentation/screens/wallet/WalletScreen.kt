package com.techgeni.walletpage.presentation.screens.wallet

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.techgeni.walletpage.R
import com.techgeni.walletpage.presentation.bottomSheets.addPromoCode.AddPromoCodeBottomSheet
import com.techgeni.walletpage.presentation.bottomSheets.addPromoCode.AddPromoCodeViewModel
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultDialog
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import com.techgeni.walletpage.presentation.screens.wallet.viewModels.CardsViewModel
import com.techgeni.walletpage.presentation.utils.theme.FigTree
import com.techgeni.walletpage.presentation.screens.wallet.viewModels.WalletViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController : NavController,
    walletViewModel: WalletViewModel = koinViewModel(),
    cardsViewModel: CardsViewModel = koinViewModel(),
    addPromoCodeViewModel: AddPromoCodeViewModel = koinViewModel()
) {

    var isCashEnabled by remember { mutableStateOf(false) }
    var isCardsEnabled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var actionResultDialogShow by remember { mutableStateOf(false) }
    val walletState by walletViewModel.walletState.collectAsState()
    val cardsState by cardsViewModel.cardsState.collectAsState()
    val cards by cardsViewModel.cards.collectAsState()
    val myWallet by walletViewModel.walletInfo.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0) { cards.size }

    val animatedStartPadding by animateDpAsState(
        targetValue = if (pagerState.currentPage == 0) 15.dp else 40.dp,
        label = "startPadding"
    )

    val animatedEndPadding by animateDpAsState(
        targetValue = if (pagerState.currentPage == pagerState.pageCount - 1) 15.dp else 40.dp,
        label = "endPadding"
    )

    LaunchedEffect(Unit) {
        actionResultDialogShow = true
        walletViewModel.getWallet()
        cardsViewModel.getCards()
    }

    LaunchedEffect(pagerState.currentPage) {
        cardsViewModel.selectCard(pagerState.currentPage)
    }

    LaunchedEffect(myWallet, cards) {
        isCashEnabled = myWallet?.activeMethod == "cash"
        isCardsEnabled = myWallet?.activeMethod == "card"
        Log.e("MethodChanged", "WalletScreen: $isCashEnabled / $isCardsEnabled")
        if (!cards.any { it.isSelected } && myWallet?.activeMethod == "card") {
            cards.forEachIndexed { index, data ->
                if (data.id == myWallet?.activeCardId){
                    pagerState.scrollToPage(index)
                    cardsViewModel.selectCard(index)
                }
            }
        }
        if (!cards.any { it.isSelected } && myWallet?.activeMethod == "cash") {
            cardsViewModel.selectCard(pagerState.currentPage)
        }
    }

    fun putPayMethod(retryRequest : String? = null) {
        actionResultDialogShow = true
        if (retryRequest.isNullOrEmpty() || retryRequest == "putPayMethod") {
            walletViewModel.putPayment(
                activeMethod = if (isCashEnabled) "cash" else "card",
                cardId = if (isCashEnabled) 0 else cards[pagerState.currentPage].id
            )
        }

    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Column {

            Text(
                "Wallet",
                modifier = Modifier.padding(start = 15.dp),
                fontFamily = FigTree,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp
            )

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(
                    start = animatedStartPadding,
                    end = animatedEndPadding
                ),
                beyondViewportPageCount = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) { page->

                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue.coerceIn(0f, 1f)
                val scale = 1f - (pageOffset * 0.12f)


                BalanceCard(scale = scale, data = cards[page])
            }

            Spacer(modifier = Modifier.height(20.dp))

            IdentificationCard(modifier = Modifier.padding(horizontal = 15.dp))

            Spacer(modifier = Modifier.height(20.dp))

            WalletAdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_promo_code,
                text = "Add promo code",
                enabledSwitch = false,
                isChecked = false,
                onCheckedChange = {},
                onClicked = {
                    showBottomSheet = true
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            WalletAdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_cash,
                text = "Cash",
                enabledSwitch = true,
                isChecked = isCashEnabled,
                onCheckedChange = {
                    isCashEnabled = it
                    isCardsEnabled = !it
                    putPayMethod()
                },
                onClicked = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            WalletAdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_cards,
                text = "Card ****7777",
                enabledSwitch = true,
                isChecked = isCardsEnabled,
                onCheckedChange = {
                    isCardsEnabled = it
                    isCashEnabled = !it
                    putPayMethod()
                },
                onClicked = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            WalletAdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_add_card,
                text = "Add new card",
                enabledSwitch = false,
                isChecked = false,
                onCheckedChange = {},
                onClicked = {
                    navController.navigate("addCard")
                }
            )
        }
    }

    //Additional
    ActionResultDialog(
        isShow = actionResultDialogShow,
        states = listOf(walletState, cardsState),
        onDismiss = {
            if (walletViewModel.hideDialog() && cardsViewModel.hideDialog()) {
                actionResultDialogShow = false
            }
        }
    ) { retryRequest ->

        putPayMethod(retryRequest)
        walletViewModel.getWallet(retryRequest)
        cardsViewModel.getCards(retryRequest)

        actionResultDialogShow = true
    }
    
    AddPromoCodeBottomSheet(
        sheetState = sheetState,
        showBottomSheet = showBottomSheet,
        addPromoCodeViewModel = addPromoCodeViewModel,
        onDismissRequest = { showBottomSheet = false }
    )

}