package register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import components.ProgressBar
import components.SnackBarMessage
import screens.Screens

class Register : Ui<RegisterState> {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(state: RegisterState, modifier: Modifier) {

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = { state.eventSink(RegisterEvent.Pop) }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                                contentDescription = "",
                            )
                        }
                    },
                    modifier = modifier.fillMaxWidth()
                )
            },
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
                    .fillMaxSize(),
            ) {


                Text(
                    text = "Register",
                    fontSize = 32.sp,
                    modifier = modifier
                        .padding(top = 100.dp, start = 24.dp)
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
                                state.eventSink(RegisterEvent.Email(it))
                            },
                            modifier = modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Email"
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
                                state.eventSink(RegisterEvent.Password(it))
                            },
                            modifier = modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Password"
                                )
                            },
                            isError = state.passwordError?.isNotBlank() == true,
                            visualTransformation = PasswordVisualTransformation(),
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

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OutlinedTextField(
                            value = state.confirmPassword,
                            onValueChange = {
                                state.eventSink(RegisterEvent.ConfirmPassword(it))
                            },
                            modifier = modifier.fillMaxWidth(),
                            label = {
                                Text(
                                    text = "Confirm Password"
                                )
                            },
                            isError = state.confirmPasswordError?.isNotBlank() == true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            )
                        )

                        if (state.confirmPasswordError?.isNotBlank() == true) {
                            Text(
                                text = state.confirmPasswordError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = modifier.padding(start = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = modifier.padding(12.dp))

                    Button(
                        onClick = {
                            state.eventSink(RegisterEvent.Submit)
                        },
                        modifier = modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Register"
                        )
                    }
                }

                Box(
                    modifier = modifier
                        .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                ) {
                    OutlinedButton(
                        onClick = {

                        },
                        modifier = modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = "Login"
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
                is Screens.RegisterScreen -> Register()
                else -> null
            }
        }
    }

}

@Composable
fun RegisterScreenContentPreview() {
    Register().Content(state = RegisterState(eventSink = {}), modifier = Modifier)
}