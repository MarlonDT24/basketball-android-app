package com.matosa.basketallapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.matosa.basketallapp.data.MatchEntity
import com.matosa.basketallapp.data.PlayerStatsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMatchesScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedMatchId by remember { mutableStateOf<String?>(null) }

    // Datos mock de partidos
    val matches = remember {
        listOf(
            MatchEntity(
                id = "1",
                homeTeam = "CREVILLOR C.B.C MANISES-QUART",
                awayTeam = "ESCRITORES ALZIRA",
                homeScore = 39,
                awayScore = 35,
                matchDate = "23/11/2025 15:30",
                status = "live",
                venue = "Junior Masculino Nivel Autonómico",
                quarter = "4T",
                timeRemaining = "12:30"
            ),
            MatchEntity(
                id = "2",
                homeTeam = "BOCAIRENT",
                awayTeam = "NOU BASQUET FAURA CASTELLO",
                homeScore = 16,
                awayScore = 32,
                matchDate = "23/11/2025 17:30",
                status = "live",
                venue = "Senior Juvenil",
                quarter = "2T",
                timeRemaining = "08:45"
            )
        )
    }

    // Datos mock de estadísticas de jugadores
    val playerStats = remember {
        listOf(
            PlayerStatsEntity(1, "1", "HUGO GÁLVEZ UR", "CREVILLOR", 19, 5, 2, 3, 28),
            PlayerStatsEntity(2, "1", "ALEXANDER COLLERNS ZARANDAGDADA", "CREVILLOR", 11, 8, 1, 2, 25),
            PlayerStatsEntity(3, "1", "VICTOR GALLÉN FERRER", "CREVILLOR", 10, 6, 3, 1, 22),
            PlayerStatsEntity(4, "1", "GUILLEM MAROTO DAVID", "CREVILLOR", 8, 3, 5, 4, 20),
            PlayerStatsEntity(5, "1", "PAU BOVO MONTAÑO", "CREVILLOR", 6, 4, 2, 1, 18),
            PlayerStatsEntity(6, "1", "BRUNO JIMÉNEZ VICO", "ESCRITORES", 15, 7, 4, 2, 30),
            PlayerStatsEntity(7, "1", "CRISTIAN DELLA VECCHIA", "ESCRITORES", 12, 5, 3, 3, 25),
            PlayerStatsEntity(8, "1", "MARCOS BLANCO", "ESCRITORES", 8, 4, 6, 1, 22)
        )
    }

    val filteredMatches = matches.filter {
        it.homeTeam.contains(searchQuery, ignoreCase = true) ||
                it.awayTeam.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Partidos en Directo",
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
                .background(Color(0xFF1E3A8A))
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Instrucciones
            Text(
                text = "Partidos en directo en este momento",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ícono
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📺",
                    fontSize = 40.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por club, equipo o categoría") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección EN DIRECTO
            Text(
                text = "EN DIRECTO",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Lista de partidos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredMatches) { match ->
                    MatchCard(
                        match = match,
                        playerStats = playerStats.filter { it.matchId == match.id },
                        isExpanded = selectedMatchId == match.id,
                        onToggleExpand = {
                            selectedMatchId = if (selectedMatchId == match.id) null else match.id
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MatchCard(
    match: MatchEntity,
    playerStats: List<PlayerStatsEntity>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Información del partido
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Equipo local
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color(0xFFFF6B35), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🏀",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.homeTeam.split(" ").take(2).joinToString(" "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Marcador y tiempo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = match.homeScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = " - ",
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = match.awayScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Text(
                        text = "${match.quarter} ${match.timeRemaining}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    if (match.status == "live") {
                        Box(
                            modifier = Modifier
                                .background(Color.Red, CircleShape)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "EN VIVO",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Equipo visitante
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color(0xFF1E3A8A), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "⚡",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.awayTeam.split(" ").take(2).joinToString(" "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Información del evento
            Text(
                text = match.venue ?: "",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Fase Regular",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Botón para ver partido en directo
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onToggleExpand,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E3A8A)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = if (isExpanded) "OCULTAR ESTADÍSTICAS" else "VER PARTIDO EN DIRECTO",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }

            // Estadísticas expandibles
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))

                // Pestañas como en el proyecto
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TabButton(text = "DETALLES", isSelected = true)
                    TabButton(text = "ESTADÍSTICA", isSelected = false)
                    TabButton(text = "CALENDARIO", isSelected = false)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Estadísticas del partido
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard("PARTIDOS", "11")
                    StatCard("VICTORIAS", "3")
                    StatCard("DERROTAS", "8")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tiros del equipo (gráficos circulares simulados)
                Text(
                    text = "TIROS DEL EQUIPO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircularStatCard("T1", "11/15", "73%", Color(0xFF4CAF50))
                    CircularStatCard("T2", "12/30", "40%", Color(0xFFFF9800))
                    CircularStatCard("T3", "8/20", "40%", Color(0xFFF44336))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Jugadores/as
                Text(
                    text = "JUGADORES/AS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )

                // Cabecera de estadísticas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("JUGADOR/A", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(3f))
                    Text("PJ", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                    Text("PT", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                    Text("MIN", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("MIN%", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }

                // Lista de jugadores
                playerStats.forEach { player ->
                    PlayerStatRow(player)
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CircularStatCard(title: String, fraction: String, percentage: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = percentage,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = fraction,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun PlayerStatRow(player: PlayerStatsEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(3f)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF1E3A8A), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = player.playerName.take(1),
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = player.playerName.take(20),
                fontSize = 10.sp,
                color = Color.Black
            )
        }

        Text(text = "11", fontSize = 10.sp, modifier = Modifier.weight(0.8f))
        Text(text = player.points.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.8f))
        Text(text = player.minutes.toString(), fontSize = 10.sp, modifier = Modifier.weight(1f))
        Text(text = "${player.minutes * 4}%", fontSize = 10.sp, modifier = Modifier.weight(1f))
    }
}