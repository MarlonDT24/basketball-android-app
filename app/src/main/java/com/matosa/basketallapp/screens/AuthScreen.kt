package com.matosa.basketallapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.matosa.basketallapp.presentation.viewmodel.AuthViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = viewModel() ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLogin by remember { mutableStateOf(true) }

    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // Cambios de estado
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                navController.navigate("main") {
                    popUpTo("auth") { inclusive = true }
                }
            }
            is AuthViewModel.AuthState.Error -> {

            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo y título
        Text(
            text = "🏀",
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isLogin) "Iniciar Sesión" else "Registrarse",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = authState !is AuthViewModel.AuthState.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            enabled = authState !is AuthViewModel.AuthState.Loading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mensajes de error
        authState?.let { state ->
            if (state is AuthViewModel.AuthState.Error) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    if (isLogin) {
                        authViewModel.loginUser(email, password)
                    } else {
                        authViewModel.registerUser(email, password)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E3A8A)
            ),
            enabled = authState !is AuthViewModel.AuthState.Loading &&
                    email.isNotBlank() && password.isNotBlank()
        ) {
            if (authState is AuthViewModel.AuthState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    text = if (isLogin) "ENTRAR" else "REGISTRARSE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                isLogin = !isLogin
                authViewModel.resetAuthState()
                      },
            enabled = authState !is AuthViewModel.AuthState.Loading
        ) {
            Text(
                text = if (isLogin) "¿No tienes cuenta? Registrate" else "¿Ya tienes cuenta? Inicia sesión",
                color = Color(0xFF1E3A8A)
            )
        }
    }
}