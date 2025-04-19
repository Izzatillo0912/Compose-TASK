package com.techgeni.walletpage.presentation.dialogs.actionResult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.techgeni.walletpage.presentation.utils.theme.ButtonBlack
import com.techgeni.walletpage.presentation.utils.theme.ButtonGray
import com.techgeni.walletpage.presentation.utils.theme.FigTree

@Composable
fun ActionResultDialog(
    isShow : Boolean,
    states: List<ActionResultState>,
    onDismiss: () -> Unit,
    retryRequest: (String) -> Unit
) {
    if (isShow) {

        var mainState: ActionResultState = ActionResultState.Init
        val isLoading = states.any { it == ActionResultState.Loading }
        val isSuccess = states.any { it is ActionResultState.Success }
        val isError = states.any { it is ActionResultState.Error }

        if (isLoading) {
            mainState = ActionResultState.Loading
        }
        else if (isSuccess) {

            mainState = states.filterIsInstance<ActionResultState.Success>().firstOrNull()?.let {
                ActionResultState.Success(it.message)
            } ?: mainState

        }
        else if (isError) {

            mainState = states.filterIsInstance<ActionResultState.Error>().firstOrNull()?.let {
                ActionResultState.Error(it.message, it.errorType, it.retryApi)
            } ?: mainState

        }
        else {
            onDismiss()
        }


        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isLoading) Color.Transparent else Color(
                            0x40000000
                        )
                    )
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        draggedElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(
                            if (isLoading) 0.3f else 0.9f
                        )
                        .align(Alignment.Center),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isLoading) Color(0x50000000) else Color.White
                    )
                ) {
                    when (mainState) {
                        is ActionResultState.Loading -> LoadingDialog()
                        is ActionResultState.Success -> SuccessDialog(
                            message = mainState.message,
                            onConfirm = onDismiss
                        )
                        is ActionResultState.Error -> ErrorDialog(state = mainState) {
                            if (it) retryRequest(mainState.retryApi) else onDismiss()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingDialog() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("anim_loading.json"))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(all = 15.dp)
    )
}

@Composable
private fun SuccessDialog(
    message: String,
    onConfirm: () -> Unit
) {
    if (message.isEmpty()) {
        onConfirm()
        return
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("anim_checked.json"))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        composition?.let {
            lottieAnimatable.animate(
                composition = it,
                iterations = 1
            )
            onConfirm()
        }
    }

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = { lottieAnimatable.progress },
            modifier = Modifier.height(200.dp).fillMaxWidth()
        )

        Text(
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
            textAlign = TextAlign.Center,
            text = message,
            color = Color.Black,
            fontFamily = FigTree,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ErrorDialog(
    state: ActionResultState.Error,
    action : (Boolean) -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(
        if (state.errorType == 1000) "anim_no_internet.json" else "anim_error.json"
    ))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = if (state.errorType == 1000) LottieConstants.IterateForever else 1
    )

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )

        Text(
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
            textAlign = TextAlign.Center,
            text = if (state.errorType == 1000) "Not connected, Connection error :(" else state.message,
            color = Color.Black,
            fontFamily = FigTree,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Row {
            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGray,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = { action(false) }
            ) {
                Text(
                    text = "Cancel",
                    fontFamily = FigTree,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBlack,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = { action(true) }
            ) {
                Text(
                    text = "Retry",
                    fontFamily = FigTree,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

}