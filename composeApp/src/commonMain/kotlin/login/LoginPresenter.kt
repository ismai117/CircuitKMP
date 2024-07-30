package login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import screens.Screens
import utils.Validation

class LoginPresenter(
    private val navigator: Navigator,
    private val validation: Validation
) : Presenter<LoginState> {

    @Composable
    override fun present(): LoginState {

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var status by rememberSaveable { mutableStateOf(false) }
        var error by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf<String?>(null) }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        fun validateInputs(): Boolean {
            val emailResult = validation.validateEmail(email)
            val passwordResult = validation.validatePassword(password)

            val hasError = listOf(
                emailResult,
                passwordResult
            ).any { !it.successful }

            if (hasError) {
                emailError = emailResult.errorMessage
                passwordError = passwordResult.errorMessage
            } else {
                emailError = null
                passwordError = null
            }

            return !hasError
        }

        fun submit() {

            if (!validateInputs()) {
                return
            }

        }

        return LoginState(
            isLoading = isLoading,
            status = status,
            error = error,
            email = email,
            emailError = emailError,
            password = password,
            passwordError = passwordError,
            passwordVisible = passwordVisible
        ) { event ->
            when (event) {
                is LoginEvent.Email -> {
                    email = event.email
                }

                is LoginEvent.Password -> {
                    password = event.password
                }

                is LoginEvent.ShowPassword -> {
                    passwordVisible = event.visible
                }

                LoginEvent.Submit -> {
                    submit()
                }

                LoginEvent.ResetMessage -> {

                }

                LoginEvent.Clear -> {

                }

                LoginEvent.NavigateToRegisterScreen -> {
                    navigator.goTo(Screens.RegisterScreen)
                }

                LoginEvent.NavigateToForgetPasswordScreen -> {
                    navigator.goTo(Screens.ForgetPasswordScreen)
                }

                LoginEvent.Pop -> {
                    navigator.pop()
                }

            }
        }
    }

    class Factory(
        private val validation: Validation
    ) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return when (screen) {
                is Screens.LoginScreen -> LoginPresenter(navigator, validation)
                else -> null
            }
        }

    }

}