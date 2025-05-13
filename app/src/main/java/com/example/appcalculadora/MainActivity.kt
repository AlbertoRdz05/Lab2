package com.example.appcalculadora

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen { username ->
                val intent = Intent(this@MainActivity, MainActivity2::class.java).apply {
                    putExtra("USERNAME", username)
                }
                startActivity(intent)
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }

    fun isValidPassword(pwd: String): Boolean {
        if (pwd.length <= 8) return false
        if (!pwd.any { it.isUpperCase() }) return false
        if (!pwd.any { it.isLowerCase() }) return false
        if (!pwd.any { it.isDigit() }) return false
        if (!pwd.contains('_')) return false
        return true
    }

    LaunchedEffect(password) {
        if (isTyping) {
            passwordError = if (password.isNotEmpty() && !isValidPassword(password)) {
                "La contraseña debe tener:\n- Más de 8 caracteres\n- 1 mayúscula\n- 1 minúscula\n- 1 número\n- 1 guión bajo (_)"
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier
                .size(250.dp)
                .padding(bottom = 24.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isTyping = true
                },
                label = { Text("Clave") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError.isNotEmpty(),
                supportingText = {
                    if (passwordError.isNotEmpty()) {
                        Text(text = passwordError, color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    isTyping = false
                    when {
                        username.isBlank() -> {
                            passwordError = "Ingrese un nombre de usuario"
                        }
                        !isValidPassword(password) -> {
                            passwordError = "La contraseña no cumple los requisitos"
                        }
                        else -> {
                            passwordError = ""
                            onLoginSuccess(username)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text("Acceder")
            }
        }
    }
}