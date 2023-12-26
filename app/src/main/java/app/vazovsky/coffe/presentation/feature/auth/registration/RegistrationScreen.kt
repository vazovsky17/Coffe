package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.extensions.orDefault
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.Space
import app.vazovsky.coffe.presentation.view.TopBar

@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val email = viewModel.emailLiveData.observeAsState().value
    val password = viewModel.passwordLiveData.observeAsState().value
    val repeatPassword = viewModel.repeatPasswordLiveData.observeAsState().value

    val token = viewModel.tokenLiveData.observeAsState().value

    LaunchedEffect(token) {
        token?.let {
            navigateToMain()
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.registration_topbar_title))
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding(), start = 18.dp, end = 18.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.registration_email))
            Space(8.dp)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email.orDefault(),
                onValueChange = { text ->
                    viewModel.emailLiveData.value = text
                },
            )
            Space(24.dp)

            Text(text = stringResource(R.string.registration_password))
            Space(8.dp)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = password.orDefault(),
                onValueChange = { text ->
                    viewModel.passwordLiveData.value = text
                },
            )
            Space(24.dp)

            Text(text = stringResource(R.string.registration_repeat_password))
            Space(8.dp)
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = repeatPassword.orDefault(),
                onValueChange = { text ->
                    viewModel.repeatPasswordLiveData.value = text
                },
            )
            Space(24.dp)

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration_confirm),
                onClick = {
                    // TODO сделать проверку на пустое и на повторенный пароль и отобразить какой-нибудь снекбар
                    viewModel.register(
                        login = email.orDefault(),
                        password = password.orDefault(),
                    )
                },
            )
            Space(8.dp)

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navigateToLogin() },
                text = stringResource(R.string.registration_yet_registered),
            )
        }
    }
}