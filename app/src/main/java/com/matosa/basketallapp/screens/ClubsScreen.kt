package com.matosa.basketallapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matosa.basketallapp.data.ClubEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val clubs = remember {
        listOf(
            ClubEntity(
                id = 1,
                clubName = "CLUB BALONCESTO CERÁMICA MANISES",
                contactPerson = "RAMÓN LLUFÍN",
                phone = "639724816",
                email = "CBCMANISES@GMAIL.COM",
                address = "MANISES",
                category = "SENIOR MASCULINO NIVEL AUTONÓMICO"
            ),
            ClubEntity(
                id = 2,
                clubName = "VALENCIA BASKET CLUB",
                contactPerson = "JUAN MARTÍNEZ",
                phone = "963456789",
                email = "INFO@VALENCIABASKET.COM",
                address = "VALENCIA",
                category = "PRIMERA DIVISIÓN"
            ),
            ClubEntity(
                id = 3,
                clubName = "CB CASTELLÓN",
                contactPerson = "MARÍA GARCÍA",
                phone = "964123456",
                email = "CONTACTO@CBCASTELLON.COM",
                address = "CASTELLÓN",
                category = "LEB ORO"
            ),
            ClubEntity(
                id = 4,
                clubName = "ALICANTE BASKETBALL",
                contactPerson = "CARLOS LÓPEZ",
                phone = "965789123",
                email = "INFO@ALICANTEBASKET.ES",
                address = "ALICANTE",
                category = "LEB PLATA"
            )
        )
    }

    val filteredClubs = clubs.filter {
        it.clubName.contains(searchQuery, ignoreCase = true) ||
                it.contactPerson?.contains(searchQuery, ignoreCase = true) == true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Clubes",
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
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Introduce el nombre del club") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E3A8A),
                    focusedLabelColor = Color(0xFF1E3A8A)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de clubes
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredClubs) { club ->
                    ClubCard(club = club)
                }
            }
        }
    }
}

@Composable
fun ClubCard(club: ClubEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = club.clubName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3A8A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton(text = "DETALLES", isSelected = true)
                TabButton(text = "ESTADÍSTICA", isSelected = false)
                TabButton(text = "CALENDARIO", isSelected = false)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "CONTACTO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )

                club.contactPerson?.let {
                    ContactInfo(
                        icon = "👤",
                        text = it
                    )
                }

                club.phone?.let {
                    ContactInfo(
                        icon = "📞",
                        text = it
                    )
                }

                club.email?.let {
                    ContactInfo(
                        icon = "📧",
                        text = it
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "FICHA DE EQUIPO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )

                club.category?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "EQUIPACIÓN",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Text(
                    text = "CAMISETA: NARANJA / NEGRO",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Text(
                    text = "PANTALONES: NARANJA / NEGRO",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Favorito",
                    tint = Color(0xFFFF6B35),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "AÑADIR FAVORITO",
                    fontSize = 12.sp,
                    color = Color(0xFFFF6B35),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) Color(0xFF1E3A8A) else Color.Gray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Gray
        )
    }
}

@Composable
fun ContactInfo(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}