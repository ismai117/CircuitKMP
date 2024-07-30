import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import forgetPassword.ForgetPassword
import forgetPassword.ForgetPasswordPresenter
import login.Login
import login.LoginPresenter
import org.jetbrains.compose.ui.tooling.preview.Preview
import register.Register
import register.RegisterPresenter
import screens.Screens
import starter.Starter
import starter.StarterPresenter
import utils.Validation
import utils.ValidationImpl

@Preview
@Composable
fun App(
    circuit: Circuit = buildCircuit(),
) = MaterialTheme {
    val backStack = rememberSaveableBackStack(Screens.StarterScreen)
    val navigator = rememberCircuitNavigator(backStack, {})
    CircuitCompositionLocals(circuit) {
        NavigableCircuitContent(navigator = navigator, backStack = backStack)
    }
}

fun buildCircuit(
    validation: Validation = ValidationImpl(),
): Circuit {

    val presenterFactories: List<Presenter.Factory> = listOf(
        StarterPresenter.Factory(),
        LoginPresenter.Factory(validation),
        RegisterPresenter.Factory(validation),
        ForgetPasswordPresenter.Factory(validation)
    )

    val uiFactories : List<Ui.Factory> = listOf(
        Starter.Factory(),
        Login.Factory(),
        Register.Factory(),
        ForgetPassword.Factory()
    )

    return Circuit.Builder()
        .addPresenterFactories(presenterFactories)
        .addUiFactories(uiFactories)
        .build()
}