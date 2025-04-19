package com.techgeni.walletpage.presentation.screens.wallet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techgeni.walletpage.R
import com.techgeni.walletpage.presentation.utils.elements.CustomSwitch

@Composable
fun WalletAdditionalCard(
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