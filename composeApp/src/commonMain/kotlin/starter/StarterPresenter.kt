package starter

import androidx.compose.runtime.Composable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import screens.Screens

class StarterPresenter(
    private val navigator: Navigator
) : Presenter<StarterState> {

    @Composable
    override fun present(): StarterState {
        return StarterState(
            eventSink = { event ->
                when (event) {
                    StarterEvent.NavigateToLoginScreen -> {
                        navigator.goTo(Screens.LoginScreen)
                    }
                    StarterEvent.NavigateToRegisterScreen -> {
                        navigator.goTo(Screens.RegisterScreen)
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
            return when(screen){
                is Screens.StarterScreen ->  StarterPresenter(navigator)
                else -> null
            }
        }
    }

}