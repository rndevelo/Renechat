package com.rndeveloper.renechat.ui.chat.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rndeveloper.renechat.model.Message
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatItem(sms: Message, myUid: String) {
    val timeFormat =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(sms.time.toLong())
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Card(
            modifier = if (sms.uid == myUid)
                Modifier.align(Alignment.TopEnd)
            else Modifier.align(Alignment.TopStart),
            shape = RoundedCornerShape(1.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sms.text,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = timeFormat,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
    }
}