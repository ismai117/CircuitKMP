package forgetPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import components.ProgressBar
import components.SnackBarMessage
import login.LoginScreen
import register.RegisterScreen
import register.RegisterScreenContent

data object ForgetPasswordScreen : Screen {
    data class State(

        val isLoading: Boolean = false,
        val status: Boolean = false,
        val error: String = "",

        val email: String = "",
        val emailError: String? = "",

        val eventSink: (Event) -> Unit

    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        class Email(val email: String) : Event()
        data object Pop : Event()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreenContent(
    modifier: Modifier = Modifier,
    state: ForgetPasswordScreen.State
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
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
                text = "Reset Password",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
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
                            state.eventSink(ForgetPasswordScreen.Event.Email(it))
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

                Spacer(modifier = modifier.padding(12.dp))

                Button(
                    onClick = {

                    },
                    modifier = modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Submit"
                    )
                }
            }

        }

        ProgressBar(isLoading = state.isLoading)

    }

}

@Composable
fun ForgetPasswordScreenContentPreview(){
    ForgetPasswordScreenContent(state = ForgetPasswordScreen.State(eventSink = {}))
}