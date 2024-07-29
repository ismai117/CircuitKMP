package starter

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import login.LoginScreen
import register.RegisterScreen

class StarterPresenter(
    private val navigator: Navigator
) : Presenter<StarterScreen.State> {

    @Composable
    override fun present(): StarterScreen.State {
        return StarterScreen.State(
            eventSink = { event ->
                when (event) {
                    StarterScreen.Event.NavigateToLoginScreen -> {
                        navigator.goTo(LoginScreen)
                    }
                    StarterScreen.Event.NavigateToRegisterScreen -> {
                        navigator.goTo(RegisterScreen)
                    }
                }
            }
        )
    }

    class Factory : Presenter.Factory {
        override fun create(
            screen: Screen,
            navigator: Navigator,
            context: CircuitContext
        ): Presenter<*>? {
            when(screen){
                StarterScreen -> return StarterPresenter(navigator)
                else -> return null
            }
        }
    }

}