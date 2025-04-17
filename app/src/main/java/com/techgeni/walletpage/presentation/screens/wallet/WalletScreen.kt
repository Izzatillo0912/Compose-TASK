package com.techgeni.walletpage.presentation.screens.wallet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.techgeni.walletpage.R
import com.techgeni.walletpage.presentation.bottomSheets.addPromoCode.AddPromoCodeBottomSheet
import com.techgeni.walletpage.presentation.utils.elements.CustomSwitch
import com.techgeni.walletpage.presentation.utils.theme.FigTree
import com.techgeni.walletpage.utils.RemoteResult
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController : NavController,
    viewModel: WalletViewModel = koinViewModel()
) {

    var isCashEnabled by remember { mutableStateOf(true) }
    var isCardsEnabled by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val state by viewModel.walletState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getWallet()
    }

    when (val state = state) {
        is RemoteResult.Loading -> {
            Log.e("GetWallet", "WalletScreen: LOADING")
            CircularProgressIndicator()
        }
        is RemoteResult.Success -> {
            Log.e("GetWallet", "WalletScreen: ${state.data}")
        }
        is RemoteResult.Error -> {
            Log.e("GetWallet", "WalletScreen: ${state.error}")
        }
        RemoteResult.Init -> {
            Log.e("GetWallet", "WalletScreen: INITIAL")
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
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

            Spacer(modifier = Modifier.height(20.dp))

            BalanceCard(modifier = Modifier.padding(horizontal = 15.dp))

            Spacer(modifier = Modifier.height(20.dp))

            IdentificationCard(modifier = Modifier.padding(horizontal = 15.dp))

            Spacer(modifier = Modifier.height(20.dp))

            AdditionalCard(
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

            AdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_cash,
                text = "Cash",
                enabledSwitch = true,
                isChecked = isCashEnabled,
                onCheckedChange = {isCashEnabled = it},
                onClicked = {

                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            AdditionalCard(
                modifier = Modifier.padding(horizontal = 15.dp),
                icon = R.drawable.ic_cards,
                text = "Card ****7777",
                enabledSwitch = true,
                isChecked = isCardsEnabled,
                onCheckedChange = {isCardsEnabled = it},
                onClicked = {

                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            AdditionalCard(
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
    
    AddPromoCodeBottomSheet(
        sheetState = sheetState,
        showBottomSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false }
    ) {
        
    }
}

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF1B1B1B), Color(0xFF3C3C3C)),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Column {
            Text(
                text = "Balance",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "0,000.00",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun IdentificationCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                color = Color.Black, width = 1.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "Identification")
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Identification required",
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(painter = painterResource(id = R.drawable.ic_arrow_up), contentDescription = "")
        }
    }
}

@Composable
fun AdditionalCard(
    modifier: Modifier = Modifier,
    icon : Int,
    text : String,
    enabledSwitch : Boolean,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClicked : ()-> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .graphicsLayer {
                shadowElevation = 1.dp.toPx() // y=1
                shape = RoundedCornerShape(14.dp)
                clip = false
            }
            .drawBehind {
                val shadowColor = Color(0x33000000) // qora, 20% opacity
                val cornerRadius = 10.dp.toPx()

                drawRoundRect(
                    color = shadowColor,
                    topLeft = Offset(0f, 1.dp.toPx()), // y = 1
                    size = Size(size.width, size.height),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                    alpha = 1f
                )
            }
            .background(
                color = Color(0xFFF7F8FC),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                indication = if (enabledSwitch) null else LocalIndication.current,
                interactionSource = remember { MutableInteractionSource() }) {
                onClicked()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Cash Icon (Rasm)
                Image(
                    painter = painterResource(id = icon), // rasmni joylashtir
                    contentDescription = "Cash",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Cash text
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }

            if (enabledSwitch) {
                CustomSwitch(
                    onCheckedChange = onCheckedChange,
                    checked = isChecked
                )
            }else {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "")
            }
        }
    }

}