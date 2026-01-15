package com.matosa.basketallapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.matosa.basketallapp.data.PlayerProfileEntity
import com.matosa.basketallapp.presentation.viewmodel.PlayerProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileScreen(
    navController: NavController,
    viewModel: PlayerProfileViewModel = viewModel()
) {
    val profile by viewModel.profile.observeAsState(PlayerProfileEntity())
    val saveState by viewModel.saveState.observeAsState(PlayerProfileViewModel.SaveState.Idle)

    var playerName by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var federationNumber by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }

    // Inicializa los campos cuando se carga el perfil
    LaunchedEffect(profile) {
        playerName = profile.playerName
        teamName = profile.teamName
        position = profile.position
        federationNumber = profile.federationNumber
        birthDate = profile.birthDate
        phone = profile.phone
        email = profile.email
        address = profile.address
        emergencyContact = profile.emergencyContact
    }

    var showSuccessMessage by remember { mutableStateOf(false) }

    LaunchedEffect(saveState) {
        when (saveState) {
            is PlayerProfileViewModel.SaveState.Success -> {
                println("Perfil guardado con éxito!")
                showSuccessMessage = true
                kotlinx.coroutines.delay(2000)
                showSuccessMessage = false
                viewModel.resetSaveState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil del Jugador",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E3A8A)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE5E7EB)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.size(60.dp),
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "FICHA FEDERATIVA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Formulario
            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Nombre del jugador *") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                label = { Text("Equipo/Club") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                label = { Text("Posición") },
                placeholder = { Text("Base, Escolta, Alero, Ala-Pívot, Pívot") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = federationNumber,
                onValueChange = { federationNumber = it },
                label = { Text("Número federativo") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = emergencyContact,
                onValueChange = { emergencyContact = it },
                label = { Text("Contacto de emergencia") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar mensaje de error si hay
            if (saveState is PlayerProfileViewModel.SaveState.Error) {
                Text(
                    text = (saveState as PlayerProfileViewModel.SaveState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (showSuccessMessage) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text(
                        text = "Perfil guardado correctamente",
                        color = Color.White,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        val newProfile = PlayerProfileEntity(
                            id = profile.id,
                            playerName = playerName,
                            teamName = teamName,
                            position = position,
                            federationNumber = federationNumber,
                            birthDate = birthDate,
                            phone = phone,
                            email = email,
                            address = address,
                            emergencyContact = emergencyContact,
                            createdAt = profile.createdAt
                        )
                        viewModel.saveProfile(newProfile)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E3A8A)
                    ),
                    enabled = saveState !is PlayerProfileViewModel.SaveState.Loading &&
                            playerName.isNotBlank()
                ) {
                    if (saveState is PlayerProfileViewModel.SaveState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Text("GUARDAR PERFIL")
                    }
                }

                // Botón Limpiar
                OutlinedButton(
                    onClick = {
                        playerName = ""
                        teamName = ""
                        position = ""
                        federationNumber = ""
                        birthDate = ""
                        phone = ""
                        email = ""
                        address = ""
                        emergencyContact = ""
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF1E3A8A)
                    )
                ) {
                    Text("LIMPIAR")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "* Campos obligatorios\nLos datos se guardan localmente en tu dispositivo",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}