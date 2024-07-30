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
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import components.ProgressBar
import components.SnackBarMessage
import screens.Screens

class Login : Ui<LoginState> {

    @Composable
    override fun Content(state: LoginState, modifier: Modifier) {

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
                                state.eventSink(LoginEvent.Email(it))
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
                                state.eventSink(LoginEvent.Password(it))
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
                                            state.eventSink(LoginEvent.ShowPassword(visible = true))
                                        } else {
                                            state.eventSink(LoginEvent.ShowPassword(visible = false))
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
                            state.eventSink(LoginEvent.NavigateToForgetPasswordScreen)
                        },
                        modifier = modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Forget Password"
                        )
                    }

                    Button(
                        onClick = {
                            state.eventSink(LoginEvent.Submit)
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
                            state.eventSink(LoginEvent.NavigateToRegisterScreen)
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

    class Factory : Ui.Factory {
        override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
            return when(screen){
                is Screens.LoginScreen -> Login()
                else -> null
            }
        }
    }

}

@Composable
fun LoginScreenContentPreview() {
    Login().Content(state = LoginState(eventSink = {}), modifier = Modifier)
}