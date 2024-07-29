package login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import components.ProgressBar
import components.SnackBarMessage
import register.RegisterScreen.Event

data object LoginScreen : Screen {
    data class State(
        val isLoading: Boolean = false,
        val status: Boolean = false,
        val error: String = "",

        val email: String = "",
        val emailError: String? = "",

        val password: String = "",
        val passwordError: String? = "",

        val passwordVisible: Boolean = false,

        val eventSink: (Event) -> Unit

    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        class Email(val email: String) : Event()
        class Password(val password: String) : Event()
        data object Submit : Event()
        data object Clear : Event()
        class ShowPassword(val visible: Boolean) : Event()
        data object ResetMessage : Event()
        data object NavigateToRegisterScreen : Event()
        data object NavigateToForgetPasswordScreen : Event()
        data object Pop : Event()
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginScreen.State
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackBarMessage(
                snackBarHostState = snackbarHostState,
                onDismiss = {}
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {


            Text(
                text = "Login",
                fontSize = 32.sp,
                modifier = modifier
                    .padding(
                        top = 100.dp,
                        start = 24.dp
                    )
            )

            Column(
                modifier = modifier
                    .padding(top = 80.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {
                            state.eventSink(LoginScreen.Event.Email(it))
                        },
                        modifier = modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = "E-mail"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = "email"
                            )
                        },
                        isError = state.emailError?.isNotBlank() == true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        )
                    )

                    if (state.emailError?.isNotBlank() == true) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = modifier.padding(start = 12.dp)
                        )
                    }

                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = {
                            state.eventSink(LoginScreen.Event.Password(it))
                        },
                        modifier = modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = "Password"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Password,
                                contentDescription = "Password"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (!state.passwordVisible) {
                                        state.eventSink(LoginScreen.Event.ShowPassword(visible = true))
                                    } else {
                                        state.eventSink(LoginScreen.Event.ShowPassword(visible = false))
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (state.passwordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                    contentDescription = "Show password",
                                )
                            }
                        },
                        isError = state.passwordError?.isNotBlank() == true,
                        visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        )
                    )

                    if (state.passwordError?.isNotBlank() == true) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colorScheme.error,
                            modifier = modifier.padding(start = 12.dp)
                        )
                    }
                }

                TextButton(
                    onClick = {
                        state.eventSink(LoginScreen.Event.NavigateToForgetPasswordScreen)
                    },
                    modifier = modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Forget Password"
                    )
                }

                Button(
                    onClick = {
                        state.eventSink(LoginScreen.Event.Submit)
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row {
                        Text(
                            text = "Login"
                        )
                    }
                }

                OutlinedButton(
                    onClick = {
                        state.eventSink(LoginScreen.Event.NavigateToRegisterScreen)
                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = "Register"
                    )
                }

            }

        }

        ProgressBar(isLoading = state.isLoading)

    }

}

@Composable
fun LoginScreenContentPreview(){
    LoginScreenContent(state = LoginScreen.State(eventSink = {}))
}