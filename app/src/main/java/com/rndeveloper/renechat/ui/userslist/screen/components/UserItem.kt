package com.rndeveloper.renechat.ui.userslist.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rndeveloper.renechat.model.UserData
import com.rndeveloper.renechat.navigation.Routes
import com.rndeveloper.renechat.ui.theme.RenechatTheme

@Composable
fun UserItem(navController: NavController, myUid: String?, item: UserData) {
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
            },
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.username)
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
        }
        Divider(Modifier.padding(horizontal = 10.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    RenechatTheme {
        UserItem(
            navController = NavController(LocalContext.current),
            item = UserData(username = "Amigo feliz"),
            myUid = "usersListViewModel.myUid()"
        )
    }
}