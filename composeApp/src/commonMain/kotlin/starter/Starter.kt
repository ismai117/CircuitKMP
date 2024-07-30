package starter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import authapp.composeapp.generated.resources.Res
import authapp.composeapp.generated.resources.google
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.Screens

class Starter : Ui<StarterState> {

    @Composable
    override fun Content(state: StarterState, modifier: Modifier) {

        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            }
        ) { paddingValues ->

            Column(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                TextButton(
                    onClick = {

                    },
                    modifier = modifier
                        .padding(top = 12.dp, end = 24.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "Skip"
                    )
                }

                Box(
                    modifier = modifier
                        .padding(top = 50.dp, start = 24.dp, end = 24.dp)
                        .size(300.dp)
                        .align(Alignment.CenterHorizontally),
//                        .border(width = 1.dp, color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    AuthAnimation()
                }

                Spacer(modifier = modifier.weight(1f))


                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val annotatedString = buildAnnotatedString {
                        append("I agree to Noor's ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("Terms of Service ")
                        }
                        append("and confirm that I have read their ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("Privacy Policy.")
                        }
                    }
                    Text(
                        text = annotatedString,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            state.eventSink(StarterEvent.NavigateToLoginScreen)
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Row {
                            Text(
                                text = "Login"
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            state.eventSink(StarterEvent.NavigateToRegisterScreen)
                        },
                        modifier = modifier.weight(1f)
                    ) {
                        Text(
                            text = "Register"
                        )
                    }

                }

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                ) {
                    OutlinedButton(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation()
                    ) {
                        Box(
                            modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.google),
                                contentDescription = "Google sign in",
                                modifier = modifier
                                    .padding(start = 12.dp)
                                    .size(24.dp)
                                    .align(Alignment.CenterStart)
                            )
                            Text(
                                text = "Sign In with Google",
                                modifier = modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

            }

        }

    }

    class Factory : Ui.Factory {
        override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
            return when(screen){
                is Screens.StarterScreen -> Starter()
                else -> null
            }
        }
    }

}

@Composable
fun StarterScreenContentPreview(){
    Starter().Content(
        state = StarterState(eventSink = {}),
        modifier = Modifier
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AuthAnimation() {

    var json by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        json = Res.readBytes("files/auth_animation.json").decodeToString()
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.JsonString(json)
    )


    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        composition = composition,
        progress = { progress }
    )

}