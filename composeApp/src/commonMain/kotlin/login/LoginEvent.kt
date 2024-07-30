package login

import com.slack.circuit.runtime.CircuitUiEvent

sealed class LoginEvent : CircuitUiEvent {
    class Email(val email: String) : LoginEvent()
    class Password(val password: String) : LoginEvent()
    data object Submit : LoginEvent()
    data object Clear : LoginEvent()
    class ShowPassword(val visible: Boolean) : LoginEvent()
    data object ResetMessage : LoginEvent()
    data object NavigateToRegisterScreen : LoginEvent()
    data object NavigateToForgetPasswordScreen : LoginEvent()
    data object Pop : LoginEvent()
}