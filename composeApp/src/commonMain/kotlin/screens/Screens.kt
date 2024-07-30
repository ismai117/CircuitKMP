package screens

import com.slack.circuit.runtime.screen.Screen

expect object Screens {
    data object StarterScreen : Screen
    data object LoginScreen : Screen
    data object RegisterScreen : Screen
    data object ForgetPasswordScreen : Screen
}