package com.techgeni.walletpage.presentation.screens.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techgeni.walletpage.domain.cards.MyCardModel
import com.techgeni.walletpage.presentation.utils.theme.ButtonGray

@Composable
fun BalanceCard(
    scale : Float,
    data : MyCardModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .background(
                if (data.isSelected) {
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF1B1B1B), Color(0xFF3C3C3C)),
                        start = Offset(0f, 0f),
                        end = Offset.Infinite
                    )
                } else {
                    SolidColor(ButtonGray) // oddiy rang (solid color)
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Column {
            Text(
                text = "Balance",
                color = if (data.isSelected) Color.White else Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "0,000.00",
                color = if (data.isSelected) Color.White else Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "${data.number.take(4)}  ****  ****  ${data.number.takeLast(4)}",
                letterSpacing = TextUnit(2f, TextUnitType.Sp),
                color = if (data.isSelected) Color.White else Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}