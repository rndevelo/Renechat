package com.rndeveloper.renechat.ui.userslist.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rndeveloper.renechat.model.UserData
import com.rndeveloper.renechat.navigation.Routes
import com.rndeveloper.renechat.ui.chat.ChatViewModel
import com.rndeveloper.renechat.ui.theme.RenechatTheme

@Composable
fun UserItem(
    navController: NavController,
    myUid: String?,
    item: UserData,
    getMessages: (String, String) -> Unit,
    chatViewModel: ChatViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier
            .clickable {
                navController.navigate(
                    route = Routes.Chat.passId(
                        myUid = myUid.toString(),
                        otherUid = item.uid
                    )
                ) {
                    launchSingleTop = true
                }
            }
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {}
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = item.username)
            }
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
        }
//        Text(text = chatState.messages?.firstOrNull()?.text ?: "")
    }

}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    RenechatTheme {
        UserItem(
            navController = NavController(LocalContext.current),
            myUid = "usersListViewModel.myUid()",
            item = UserData(username = "Amigo feliz"),
            getMessages = { _, _ -> },
        )
    }
}