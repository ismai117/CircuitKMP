package starter

import com.slack.circuit.runtime.CircuitUiState

data class StarterState(
    val eventSink: (StarterEvent) -> Unit
) : CircuitUiState