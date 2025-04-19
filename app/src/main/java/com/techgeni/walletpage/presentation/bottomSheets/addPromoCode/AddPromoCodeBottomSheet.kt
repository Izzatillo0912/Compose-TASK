@file:JvmName("AddPromoCodeViewModelKt")

package com.techgeni.walletpage.presentation.bottomSheets.addPromoCode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultDialog
import com.techgeni.walletpage.presentation.dialogs.actionResult.ActionResultState
import com.techgeni.walletpage.presentation.utils.buttons.CustomBackButton
import com.techgeni.walletpage.presentation.utils.buttons.SaveButton
import com.techgeni.walletpage.presentation.utils.theme.FigTree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPromoCodeBottomSheet(
    sheetState: SheetState,
    showBottomSheet : Boolean,
    onDismissRequest : () -> Unit,
    addPromoCodeViewModel: AddPromoCodeViewModel,
) {

    val state by addPromoCodeViewModel.addPromoCodeState.collectAsState()
    var actionResultDialogShow by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    fun sendPromoCode() {
        addPromoCodeViewModel.addPromoCode(text)
        actionResultDialogShow = true
    }

    if (showBottomSheet) {

        text = ""

        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            dragHandle = null,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    CustomBackButton(
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 15.dp)
                            .size(48.dp),
                        onClick = { onDismissRequest() }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Enter promo code",
                        color = Color.Black,
                        fontFamily = FigTree,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text(
                            "Promo code : ",
                            style = TextStyle(
                                color = Color.Gray,
                                fontFamily = FigTree,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )},
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Black,   // Pastki chiziq rangi (aktiv)
                        unfocusedIndicatorColor = Color.Gray, // Pastki chiziq rangi (noaktiv)
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Blue
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = FigTree,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(15.dp))

                SaveButton(
                    modifier = Modifier.padding(bottom = 15.dp),
                    enabled = text.isNotEmpty()
                ) {
                    sendPromoCode()
                }

                ActionResultDialog(
                    isShow = actionResultDialogShow,
                    states = listOf(state),
                    onDismiss = {
                        actionResultDialogShow = false
                        if (state is ActionResultState.Success) onDismissRequest()
                    }
                ) {
                    sendPromoCode()
                }
            }
        }
    }
}