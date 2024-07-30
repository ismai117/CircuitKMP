package forgetPassword

import com.slack.circuit.runtime.CircuitUiEvent

sealed class ForgetPasswordEvent : CircuitUiEvent {
    class Email(val email: String) : ForgetPasswordEvent()
    data object Submit : ForgetPasswordEvent()
    data object Pop : ForgetPasswordEvent()
}