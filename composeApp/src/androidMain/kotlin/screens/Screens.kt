package screens

import android.os.Parcelable
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

actual object Screens {
    @Parcelize
    actual data object StarterScreen : Screen, Parcelable
    @Parcelize
    actual data object LoginScreen : Screen, Parcelable
    @Parcelize
    actual data object RegisterScreen : Screen, Parcelable
    @Parcelize
    actual data object ForgetPasswordScreen : Screen, Parcelable
}