package forgetPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import utils.Validation

class ForgetPasswordPresenter(
    private val navigator: Navigator,
    private val validation: Validation
) : Presenter<ForgetPasswordScreen.State> {

    @Composable
    override fun present(): ForgetPasswordScreen.State {

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var status by rememberSaveable { mutableStateOf(false) }
        var error by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf<String?>(null) }

        val emailResult = validation.validateEmail(email)

        val hasError = listOf(
            emailResult
        ).any { !it.successful }

        if (hasError) {
            emailError = emailResult.errorMessage
        }

        return ForgetPasswordScreen.State() { event ->
            when (event) {
                is ForgetPasswordScreen.Event.Email -> {
                    email = event.email
                }

                ForgetPasswordScreen.Event.Pop -> {
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
            when (screen) {
                ForgetPasswordScreen -> return ForgetPasswordPresenter(navigator, validation)
                else -> return null
            }
        }
    }

}