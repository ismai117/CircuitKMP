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
import forgetPassword.ForgetPasswordScreen
import register.RegisterScreen
import utils.Validation

class LoginPresenter(
    private val navigator: Navigator,
    private val validation: Validation
) : Presenter<LoginScreen.State> {

    @Composable
    override fun present(): LoginScreen.State {

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var status by rememberSaveable { mutableStateOf(false) }
        var error by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf<String?>(null) }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        val emailResult = validation.validateEmail(email)
        val passwordResult = validation.validatePassword(password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            emailError = emailResult.errorMessage
            passwordError = passwordResult.errorMessage
        }

        return LoginScreen.State(
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
                is LoginScreen.Event.Email -> {
                    email = event.email
                }

                is LoginScreen.Event.Password -> {
                    password = event.password
                }

                is LoginScreen.Event.ShowPassword -> {
                    passwordVisible = event.visible
                }

                LoginScreen.Event.Submit -> {

                }

                LoginScreen.Event.ResetMessage -> {

                }

                LoginScreen.Event.Clear -> {

                }

                LoginScreen.Event.NavigateToRegisterScreen -> {
                    navigator.goTo(RegisterScreen)
                }

                LoginScreen.Event.NavigateToForgetPasswordScreen -> {
                    navigator.goTo(ForgetPasswordScreen)
                }

                LoginScreen.Event.Pop -> {
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
                is LoginScreen -> LoginPresenter(navigator, validation)
                else -> null
            }
        }

    }

}