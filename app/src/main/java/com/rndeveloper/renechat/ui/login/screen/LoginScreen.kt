package com.rndeveloper.renechat.ui.login.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.rndeveloper.renechat.R
import com.rndeveloper.renechat.navigation.Routes
import com.rndeveloper.renechat.ui.login.LoginUiState
import com.rndeveloper.renechat.ui.login.LoginViewModel
import com.rndeveloper.renechat.ui.login.screen.components.CardLoginScreen
import com.rndeveloper.renechat.ui.theme.RenechatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val loginUiState by viewModel.state.collectAsStateWithLifecycle()

    if (!loginUiState.errorMessage?.error.isNullOrBlank()) {
        LaunchedEffect(loginUiState.errorMessage) {
            snackBarHostState.showSnackbar(
                "Firebase Message : ${loginUiState.errorMessage!!.error}",
                "Close",
                true,
                SnackbarDuration.Long
            )
        }
    }

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)
                    viewModel.handleGoogleSignInResult(task)
                }
            }
        }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (loginUiState.isLogged) {
                navController.navigate(Routes.UsersList.routes) {
                    popUpTo(Routes.Login.routes) {
                        inclusive = true
                    }
                }
            }else{
                LoginContent(
                    loginUiState = loginUiState,
                    onClickLogin = viewModel::loginOrRegister,
                    onClickScreenState = viewModel::changeScreenState,
                    onClickGoogleButton = { startForResult.launch(viewModel.googleSignInClient.signInIntent) }
                )
            }
        }
    }
}

@Composable
fun LoginContent(
    loginUiState: LoginUiState,
    onClickLogin: (String, String) -> Unit,
    onClickGoogleButton: () -> Unit,
    onClickScreenState: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(
                when (loginUiState.isLoading) {
                    true -> 0.4f
                    false -> 1f
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CardLoginScreen(
            loginUiState = loginUiState,
            onClick = onClickLogin
        )

        Row(
            modifier = Modifier
                .padding(6.dp)
                .clickable { onClickScreenState() }
        ) {
            Text(
                text = stringResource(loginUiState.screenState.accountText),
                modifier = Modifier.padding(end = 4.dp)
            )

            // TODO: Create Typography for this text.
            Text(
                text = stringResource(loginUiState.screenState.signText),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.Bold
            )
        }

        // TODO: Review when review theme colors
        Text(text = stringResource(R.string.login_text_forgot_your_password))

        Button(
            onClick = onClickGoogleButton,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_google),
                    contentDescription = stringResource(R.string.login_icon_google),
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 6.dp)
                )
                Text(stringResource(R.string.login_text_sign_in_with_google))
            }
        }
    }

    if (loginUiState.isLoading)
        CircularProgressIndicator()

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RenechatTheme {
        LoginContent(
            loginUiState = LoginUiState(),
            onClickLogin = { _, _ -> },
            onClickGoogleButton = {},
            onClickScreenState = {},
            modifier = Modifier
        )
    }
}


