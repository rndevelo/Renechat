package com.rndeveloper.renechat.ui.userslist.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rndeveloper.renechat.ui.appuicomponents.TopBar
import com.rndeveloper.renechat.ui.theme.RenechatTheme
import com.rndeveloper.renechat.ui.userslist.UsersListViewModel
import com.rndeveloper.renechat.ui.userslist.screen.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    navController: NavController,
    usersListViewModel: UsersListViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val usersListState by usersListViewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { TopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            usersListState.users?.let { usersList ->
                items(items = usersList) { user ->
                    UserItem(
                        navController = navController,
                        myUid = usersListViewModel.myUid(),
                        item = user
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersListScreenPreview() {
    RenechatTheme {
        UsersListScreen(navController = NavController(LocalContext.current))
    }
}