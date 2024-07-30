package register

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

class RegisterPresenter(
    private val navigator: Navigator,
    private val validation: Validation
) : Presenter<RegisterState> {

    @Composable
    override fun present(): RegisterState {

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var status by rememberSaveable { mutableStateOf(false) }
        var error by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf<String?>(null) }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
        var confirmPassword by rememberSaveable { mutableStateOf("") }
        var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }

        fun validateInputs(): Boolean {
            val emailResult = validation.validateEmail(email)
            val passwordResult = validation.validatePassword(password)
            val confirmPasswordResult =
                validation.validateConfirmPassword(password, confirmPassword)

            val hasError = listOf(
                emailResult,
                passwordResult,
                confirmPasswordResult
            ).any { !it.successful }

            if (hasError) {
                emailError = emailResult.errorMessage
                passwordError = passwordResult.errorMessage
                confirmPasswordError = confirmPasswordResult.errorMessage
            } else {
                emailError = null
                passwordError = null
                confirmPasswordError = null
            }

            return !hasError
        }

        fun submit(){

            if (!validateInputs()){
                return
            }

        }

        return RegisterState(
            isLoading = isLoading,
            status = status,
            error = error,
            email = email,
            emailError = emailError,
            password = password,
            passwordError = passwordError,
            confirmPassword = confirmPassword,
            confirmPasswordError = confirmPasswordError
        ) { event ->
            when (event) {
                is RegisterEvent.Email -> {
                    email = event.email
                }

                is RegisterEvent.Password -> {
                    password = event.password
                }

                is RegisterEvent.ConfirmPassword -> {
                    confirmPassword = event.confirmPassword
                }

                RegisterEvent.Submit -> {
                    submit()
                }

                RegisterEvent.Clear -> {
                    isLoading = false
                    status = false
                    error = ""
                    email = ""
                    emailError = ""
                    password = ""
                    passwordError = ""
                    confirmPassword = ""
                    confirmPasswordError = ""
                }

                RegisterEvent.NavigateToLoginScreen -> {
                    navigator.goTo(Screens.LoginScreen)
                }

                RegisterEvent.Pop -> {
                    navigator.pop()
                }
            }
        }
    }

    class Factory(private val validation: Validation) : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            return  when (screen) {
                is Screens.RegisterScreen -> RegisterPresenter(navigator, validation)
                else -> null
            }
        }
    }

}