package com.techgeni.walletpage.presentation.screens.wallet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techgeni.walletpage.R

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