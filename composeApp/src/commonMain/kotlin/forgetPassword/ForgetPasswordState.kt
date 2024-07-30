package forgetPassword

import com.slack.circuit.runtime.CircuitUiState

data class ForgetPasswordState(

    val isLoading: Boolean = false,
    val status: Boolean = false,
    val error: String = "",

    val email: String = "",
    val emailError: String? = "",

    val eventSink: (ForgetPasswordEvent) -> Unit

) : CircuitUiState