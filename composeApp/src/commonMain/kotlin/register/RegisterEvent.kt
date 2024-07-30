package register

import com.slack.circuit.runtime.CircuitUiEvent

sealed class RegisterEvent : CircuitUiEvent {
    class Email(val email: String) : RegisterEvent()
    class Password(val password: String) : RegisterEvent()
    class ConfirmPassword(val confirmPassword: String) : RegisterEvent()
    data object Submit : RegisterEvent()
    data object Clear : RegisterEvent()
    data object NavigateToLoginScreen : RegisterEvent()
    data object Pop : RegisterEvent()
}