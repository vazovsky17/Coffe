package app.vazovsky.coffe.presentation.feature.auth.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.vazovsky.coffe.R
import app.vazovsky.coffe.extensions.orDefault
import app.vazovsky.coffe.presentation.ui.theme.CoyoteBrown
import app.vazovsky.coffe.presentation.view.AppButton
import app.vazovsky.coffe.presentation.view.AppTextField
import app.vazovsky.coffe.presentation.view.AppTopBar
import app.vazovsky.coffe.presentation.view.Space
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel: RegistrationViewModel = hiltViewModel()
    val email = viewModel.emailLiveData.observeAsState().value
    val password = viewModel.passwordLiveData.observeAsState().value
    val repeatPassword = viewModel.repeatPasswordLiveData.observeAsState().value
    val error = viewModel.errorLiveData.observeAsState().value
    val token = viewModel.tokenLiveData.observeAsState().value

    LaunchedEffect(token) {
        token?.let {
            navigateToMain()
        }
    }

    LaunchedEffect(error) {
        if (!error.isNullOrEmpty()) {
            scope.launch {
                snackbarHostState.showSnackbar(error)
            }
        }
    }


    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.registration_topbar_title))
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding(), start = 18.dp, end = 18.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            AppTextField(
                title = stringResource(R.string.registration_email),
                value = email.orDefault(),
                onValueChange = { text ->
                    viewModel.emailLiveData.value = text
                },
                placeholder = stringResource(R.string.registration_email_example),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
            Space(24.dp)

            AppTextField(
                title = stringResource(R.string.registration_password),
                value = password.orDefault(),
                onValueChange = { text ->
                    viewModel.passwordLiveData.value = text
                },
                placeholder = stringResource(R.string.registration_password_example),
                visualTransformation = PasswordVisualTransformation('*'),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            )
            Space(24.dp)

            AppTextField(
                title = stringResource(R.string.registration_repeat_password),
                value = repeatPassword.orDefault(),
                onValueChange = { text ->
                    viewModel.repeatPasswordLiveData.value = text
                },
                placeholder = stringResource(R.string.registration_password_example),
                visualTransformation = PasswordVisualTransformation('*'),
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onDoneClick = {
                    viewModel.validateAuthData(
                        login = email.orDefault(),
                        password = password.orDefault(),
                        repeatPassword = repeatPassword.orDefault(),
                    )
                },
            )
            Space(24.dp)

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration_confirm),
                onClick = {
                    viewModel.validateAuthData(
                        login = email.orDefault(),
                        password = password.orDefault(),
                        repeatPassword = repeatPassword.orDefault(),
                    )
                },
            )
            Space(8.dp)

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navigateToLogin() },
                text = stringResource(R.string.registration_yet_registered),
                style = MaterialTheme.typography.titleMedium,
                color = CoyoteBrown,
            )
        }
    }
}