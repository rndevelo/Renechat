package com.rndeveloper.renechat.ui.chat.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rndeveloper.renechat.ChatUiState
import com.rndeveloper.renechat.model.Message
import com.rndeveloper.renechat.ui.appuicomponents.TopBar
import com.rndeveloper.renechat.ui.chat.ChatViewModel
import com.rndeveloper.renechat.ui.chat.screen.components.BottomBar
import com.rndeveloper.renechat.ui.chat.screen.components.ChatItem
import com.rndeveloper.renechat.ui.theme.RenechatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    myUid: String,
    otherUid: String,
    chatViewModel: ChatViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val chatUiState by chatViewModel.state.collectAsStateWithLifecycle(lifecycleOwner)
    val snackBarHostState = remember { SnackbarHostState() }
    var text by remember { mutableStateOf("") }
    val time = android.icu.util.Calendar.getInstance().time.time
    val message = Message(text = text, time = time.toString(), uid = myUid)

    LaunchedEffect(key1 = true) {
        chatViewModel.getMessages(myUid = myUid, otherUid = otherUid)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                text = text,
                changeText = { text = it },
                setMessage = {
                    if (text.isNotEmpty()) {
                        chatViewModel.setMessage(myUid, otherUid, message)
                        text = ""
                    }
                }
            )
        },
    ) { innerPadding ->
        AnimatedVisibility(visible = chatUiState.isLoading) {
            CircularProgressIndicator()
        }
        ChatContent(
            myUid = myUid,
            chatUiState = chatUiState,
            modifier = Modifier
                .padding(innerPadding)
//                .paint(
//                    painterResource(id = R.drawable.room_background),
//                    contentScale = ContentScale.FillBounds
//                )
        )
    }
}

@Composable
private fun ChatContent(
    myUid: String,
    chatUiState: ChatUiState,
    modifier: Modifier = Modifier
) {
    val scrollState: LazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        state = scrollState,
        reverseLayout = true
    ) {
        chatUiState.messages?.let { messages ->
            items(items = messages) { sms ->
                ChatItem(sms = sms, myUid = myUid)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RenechatTheme {
        ChatContent(
            myUid = "",
            chatUiState = ChatUiState()
        )
    }
}