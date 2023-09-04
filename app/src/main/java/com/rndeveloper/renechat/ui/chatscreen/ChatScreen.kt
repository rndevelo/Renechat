package com.rndeveloper.renechat.ui.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rndeveloper.renechat.ChatUiState
import com.rndeveloper.renechat.model.Message
import com.rndeveloper.renechat.ui.theme.RenechatTheme
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(chatViewModel: ChatViewModel = hiltViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val chatUiState by chatViewModel.state.collectAsStateWithLifecycle(lifecycleOwner)
    val snackBarHostState = remember { SnackbarHostState() }
    var text by remember { mutableStateOf("") }
    val time = android.icu.util.Calendar.getInstance().time.time
    val message = Message(text = text, time = time.toString())
    val focusManager = LocalFocusManager.current


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.onSecondary)
                    .padding(14.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sinister",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                Button(
                    onClick = {
                        if (text.isNotEmpty()) {
                            chatViewModel.setMessage(message)
                            text = ""
                        }
                    },
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send message"
                    )
                }
            }
        },
    ) { innerPadding ->
        ChatContent(
            chatUiState = chatUiState, modifier = Modifier
                .padding(innerPadding)
                .clickable {
                    focusManager.clearFocus()
                }
//                .paint(
//                    painterResource(id = R.drawable.room_background),
//                    contentScale = ContentScale.FillBounds
//                )
        )
    }
}

@Composable
private fun ChatContent(
    chatUiState: ChatUiState,
    modifier: Modifier = Modifier
) {
    val scrollState: LazyListState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            state = scrollState,
            reverseLayout = true,
            horizontalAlignment = Alignment.End
        ) {
            if (chatUiState.messages != null) {
                items(items = chatUiState.messages) { sms ->
                    val timeFormat =
                        SimpleDateFormat("HH:mm", Locale.getDefault()).format(sms.time.toLong())
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .background(MaterialTheme.colorScheme.onPrimary),
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
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RenechatTheme {
        ChatScreen()
    }
}