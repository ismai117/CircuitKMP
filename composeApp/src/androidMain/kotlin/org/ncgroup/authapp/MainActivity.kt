package org.ncgroup.authapp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import forgetPassword.ForgetPasswordScreenContentPreview
import login.LoginScreenContentPreview
import register.RegisterScreenContentPreview
import starter.StarterScreenContentPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun StarterScreenPreview() {
    StarterScreenContentPreview()
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenContentPreview()
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContentPreview()
}

@Preview
@Composable
fun ForgetPasswordPreview() {
    ForgetPasswordScreenContentPreview()
}