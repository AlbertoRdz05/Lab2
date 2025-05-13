package com.example.appcalculadora

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("USERNAME") ?: "Usuario"

        setContent {
            CalculatorScreen(
                username = username,
                onExit = {
                    startActivity(Intent(this@MainActivity2, MainActivity::class.java))
                    finish()
                }
            )
        }
    }
}

@Composable
fun CalculatorScreen(
    username: String,
    onExit: () -> Unit
) {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) }
    var operation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = "Calculadora",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Usuario: $username",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("#1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("#2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                operation = "SUMAR"
                result = number1.toDoubleOrNull()?.plus(number2.toDoubleOrNull() ?: 0.0) ?: 0.0
            }) {
                Text("SUMAR")
            }

            Button(onClick = {
                operation = "RESTAR"
                result = number1.toDoubleOrNull()?.minus(number2.toDoubleOrNull() ?: 0.0) ?: 0.0
            }) {
                Text("RESTAR")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                operation = "MULTIPLICAR"
                result = number1.toDoubleOrNull()?.times(number2.toDoubleOrNull() ?: 1.0) ?: 0.0
            }) {
                Text("MULTIPLICAR")
            }

            Button(onClick = {
                operation = "DIVIDIR"
                val divisor = number2.toDoubleOrNull() ?: 1.0
                result = if (divisor != 0.0) {
                    number1.toDoubleOrNull()?.div(divisor) ?: 0.0
                } else {
                    Double.NaN
                }
            }) {
                Text("DIVIDIR")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Operación: $operation",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Resultado: $result",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón Salir SIN imagen
        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salir")
        }
    }
}