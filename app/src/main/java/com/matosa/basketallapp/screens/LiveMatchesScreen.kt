package com.matosa.basketallapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matosa.basketallapp.data.MatchEntity
import com.matosa.basketallapp.data.PlayerStatsEntity
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveMatchesScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedMatchId by remember { mutableStateOf<String?>(null) }

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

    val playerStats = remember {
        listOf(
            PlayerStatsEntity(1, "1", "HUGO GÁLVEZ UR", "CREVILLOR", 19, 5, 2, 3, 28),
            PlayerStatsEntity(
                2,
                "1",
                "ALEXANDER COLLERNS ZARANDAGDADA",
                "CREVILLOR",
                11,
                8,
                1,
                2,
                25
            ),
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E3A8A))
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

@Composable
fun MatchCard(
    match: MatchEntity,
    playerStats: List<PlayerStatsEntity>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        Text(text = "🏀", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.homeTeam.split(" ").take(2).joinToString(" "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

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
                        Text(text = "⚡", fontSize = 20.sp)
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

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onToggleExpand,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
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

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))

                // TABS FUNCIONALES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("DETALLES", "ESTADÍSTICA", "CALENDARIO").forEachIndexed { index, title ->
                        TabButton(
                            text = title,
                            isSelected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // CONTENIDO SEGÚN TAB SELECCIONADO
                when (selectedTabIndex) {
                    0 -> MatchDetailsContent(match)
                    1 -> MatchStatisticsContent(playerStats)
                    2 -> MatchCalendarContent(match)
                }
            }
        }
    }
}

@Composable
fun MatchDetailsContent(match: MatchEntity) {
    Column {
        Text(
            text = "INFORMACIÓN DEL PARTIDO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(12.dp))

        DetailRow("FECHA:", match.matchDate)
        DetailRow("ESTADO:", match.status.uppercase())
        DetailRow("CUARTO:", "${match.quarter} - ${match.timeRemaining}")
        match.venue?.let { DetailRow("PABELLÓN:", it) }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "EQUIPOS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("LOCAL", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(match.homeTeam, fontSize = 10.sp, textAlign = TextAlign.Center)
                Text(match.homeScore.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF6B35))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("VISITANTE", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(match.awayTeam, fontSize = 10.sp, textAlign = TextAlign.Center)
                Text(match.awayScore.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
            }
        }
    }
}

@Composable
fun MatchStatisticsContent(playerStats: List<PlayerStatsEntity>) {
    Column {
        // Estadísticas del partido
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("PARTIDOS", "18")
            StatCard("VICTORIAS", "12")
            StatCard("DERROTAS", "6")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tiros del equipo
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
            CircularStatCard("T1", "18/25", "72%", Color(0xFF4CAF50))
            CircularStatCard("T2", "15/32", "47%", Color(0xFFFF9800))
            CircularStatCard("T3", "8/20", "40%", Color(0xFFF44336))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "JUGADORES/AS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E3A8A))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("JUGADOR/A", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
            Text("PT", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("REB", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("AST", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("MIN", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
        }

        playerStats.forEach { player ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(player.playerName, fontSize = 10.sp, modifier = Modifier.weight(2f))
                Text(player.points.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.rebounds.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.assists.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.minutes.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun MatchCalendarContent(match: MatchEntity) {
    var selectedMonth by remember { mutableStateOf("ENERO") }

    val upcomingMatches = remember {
        listOf(
            MatchEntity(
                id = "next1",
                homeTeam = match.homeTeam,
                awayTeam = "CB ALGEMESÍ",
                homeScore = 0,
                awayScore = 0,
                matchDate = "22/01/2026 20:00",
                status = "PRÓXIMO",
                venue = "Pabellón Municipal"
            ),
            MatchEntity(
                id = "next2",
                homeTeam = "BOCAIRENT",
                awayTeam = match.awayTeam,
                homeScore = 0,
                awayScore = 0,
                matchDate = "25/01/2026 18:30",
                status = "PRÓXIMO",
                venue = "Pabellón Bocairent"
            )
        )
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E3A8A))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Mes anterior",
                tint = Color.White,
                modifier = Modifier.clickable { /* Cambiar mes */ }
            )
            Text(
                text = selectedMonth,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Mes siguiente",
                tint = Color.White,
                modifier = Modifier.clickable { /* Cambiar mes */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "PRÓXIMOS PARTIDOS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        upcomingMatches.forEach { upcomingMatch ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = upcomingMatch.homeTeam,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "VS",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = upcomingMatch.awayTeam,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = upcomingMatch.matchDate,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Surface(
                        color = Color(0xFF1E3A8A),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = upcomingMatch.status,
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
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