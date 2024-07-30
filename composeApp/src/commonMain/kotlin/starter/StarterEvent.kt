package starter

import com.slack.circuit.runtime.CircuitUiEvent

sealed class StarterEvent : CircuitUiEvent {
    data object NavigateToLoginScreen : StarterEvent()
    data object NavigateToRegisterScreen : StarterEvent()
}