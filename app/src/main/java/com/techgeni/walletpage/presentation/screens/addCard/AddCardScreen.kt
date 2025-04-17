package com.techgeni.walletpage.presentation.screens.addCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.techgeni.walletpage.R
import com.techgeni.walletpage.presentation.utils.buttons.CustomBackButton
import com.techgeni.walletpage.presentation.utils.buttons.SaveButton
import com.techgeni.walletpage.presentation.utils.masked.CreditCardVisualTransformation
import com.techgeni.walletpage.presentation.utils.masked.ExpiryDateVisualTransformation
import com.techgeni.walletpage.presentation.utils.theme.FigTree

@Composable
fun AddCardScreen(navController: NavController) {

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cardNumberIsSelected by remember { mutableStateOf(false) }
    var expireDateIsSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {

                CustomBackButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp),
                    onClick = { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Add Card",
                    color = Color.Black,
                    fontFamily = FigTree,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_add_card),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 15.dp, end = 15.dp, bottom = 15.dp))
                {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = cardNumber,
                        hint = "0000000000000000",
                        isSelected = cardNumberIsSelected,
                        onSelected = { cardNumberIsSelected = it; expireDateIsSelected = false },
                        visualTransformation = CreditCardVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomTextField(
                        modifier = Modifier.width(100.dp),
                        text = expiryDate,
                        hint = "0000",
                        isSelected = expireDateIsSelected,
                        onSelected = { expireDateIsSelected = it; cardNumberIsSelected = false },
                        visualTransformation = ExpiryDateVisualTransformation(),
                    )
                }
            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

                SaveButton(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 30.dp),
                    enabled = cardNumber.length == 16 && expiryDate.length == 4)
                {

                }

                MyCustomKeyboards {
                    if (it.isEmpty()) {
                        if (cardNumberIsSelected && cardNumber.isNotEmpty()) {
                            cardNumber = cardNumber.dropLast(1)
                        } else if (expireDateIsSelected && expiryDate.isNotEmpty()) {
                            expiryDate = expiryDate.dropLast(1)
                        }
                    }
                    else {
                        if(cardNumberIsSelected) {
                            if (cardNumber.length != 16) {
                                cardNumber += it
                            }
                            else if (expiryDate.length != 4) {
                                cardNumberIsSelected = false
                                expireDateIsSelected = true
                                expiryDate += it
                            }
                            else cardNumberIsSelected = false
                        }
                        else if (expireDateIsSelected) {
                            if (expiryDate.length != 4) {
                                expiryDate += it
                            }
                            else if (cardNumber.length != 16) {
                                cardNumberIsSelected = true
                                expireDateIsSelected = false
                                cardNumber += it
                            }
                            else expireDateIsSelected = false
                        }
                        else {
                            if (cardNumber.length != 16) {
                                cardNumberIsSelected = true
                                cardNumber += it
                            }
                            else if(expiryDate.length != 4) {
                                expireDateIsSelected = true
                                expiryDate += it
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CustomTextField(
    modifier : Modifier,
    text : String,
    hint : String,
    isSelected : Boolean,
    onSelected : (Boolean) -> Unit,
    visualTransformation: VisualTransformation
) {
    Box(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onSelected(true)
            }
            .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) Color.White else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(top = 8.dp, bottom = 8.dp, start = 15.dp, end = 15.dp),
    ) {
        Text(
            text = visualTransformation.filter(AnnotatedString(
                text.ifEmpty { hint }
            )).text.text,
            fontFamily = FigTree,
            color = if (text.isEmpty()) Color.Gray else Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun MyCustomKeyboards(
    onChange : (String) -> Unit,
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onChange("1") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("1",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("2") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("2",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("3") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("3",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onChange("4") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("4",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("5") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("5",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("6") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("6",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onChange("7") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("7",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("8") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("8",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("9") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("9",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onChange("0") },
                modifier = Modifier.weight(1f),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Text("0",
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = { onChange("") },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black
                )
            ) {
                Icon(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), painter = painterResource(id = R.drawable.ic_backspace), contentDescription = "BackSpace")
            }
        }

}