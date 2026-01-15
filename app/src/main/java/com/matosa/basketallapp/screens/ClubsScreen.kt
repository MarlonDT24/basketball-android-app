package com.matosa.basketallapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matosa.basketallapp.data.ClubEntity
import com.matosa.basketallapp.data.MatchEntity
import com.matosa.basketallapp.data.PlayerStatsEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubsScreen(modifier: Modifier = Modifier) {
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
                clubName = "C.B.C MANISES-QUART",
                contactPerson = "PABLO SANCHEZ",
                phone = "634879220",
                email = "CBCQUART@GMAIL.COM",
                address = "MANISES",
                category = "SENIOR MASCULINO NIVEL AUTONÓMICO"

            ),
            ClubEntity(
                id = 4,
                clubName = "ESCRITORES ALZIRA",
                contactPerson = "TOMAS SALAS",
                phone = "943824101",
                email = "INFO@ESCRITORESALZIRA.COM",
                address = "ALZIRA",
                category = "SENIOR MASCULINO NIVEL AUTONÓMICO"
            )
        )
    }

    val filteredClubs = clubs.filter {
        it.clubName.contains(searchQuery, ignoreCase = true) ||
                it.contactPerson?.contains(searchQuery, ignoreCase = true) == true
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar club...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
            ),
            singleLine = true
        )

        Text(
            text = "Introduce el nombre del club",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(filteredClubs) { club ->
                ClubCard(club = club)
            }
        }
    }
}

@Composable
fun ClubCard(club: ClubEntity) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = club.clubName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A),
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = Color(0xFFFF6B35),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏀",
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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

            when (selectedTabIndex) {
                0 -> ClubDetailsContent(club)
                1 -> ClubStatisticsContent(club)
                2 -> ClubCalendarContent(club)
            }
        }
    }
}

@Composable
fun ClubDetailsContent(club: ClubEntity) {
    Column {
        Text(
            text = "CONTACTO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(12.dp))

        club.contactPerson?.let {
            ContactInfo(icon = Icons.Default.Person, text = it)
            Spacer(modifier = Modifier.height(8.dp))
        }

        club.phone?.let {
            ContactInfo(icon = Icons.Default.Phone, text = it)
            Spacer(modifier = Modifier.height(8.dp))
        }

        club.email?.let {
            ContactInfo(icon = Icons.Default.Email, text = it)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "FICHA DE EQUIPO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        club.category?.let {
            Text(text = it, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "EQUIPACIÓN",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "CAMISETA: NARANJA / NEGRO", fontSize = 12.sp, color = Color.Gray)
        Text(text = "PANTALONES: NARANJA / NEGRO", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
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

@Composable
fun ClubStatisticsContent(club: ClubEntity) {
    val teamStats = remember {
        mapOf(
            "partidos" to 11,
            "victorias" to 3,
            "derrotas" to 8,
            "pf" to 839,
            "pfp" to 76,
            "pc" to 1028,
            "pcp" to 93
        )
    }

    val playerStats = remember {
        listOf(
            PlayerStatsEntity(playerName = "HUGO GALVEZ LIS", matchId = "", teamName = club.clubName, points = 278, rebounds = 10, assists = 25, fouls = 11, minutes = 0),
            PlayerStatsEntity(playerName = "ALEXANDER CILLEKENS CARRASCO", matchId = "", teamName = club.clubName, points = 277, rebounds = 8, assists = 25, fouls = 11, minutes = 0),
            PlayerStatsEntity(playerName = "VICTOR GALLEN FERRER", matchId = "", teamName = club.clubName, points = 182, rebounds = 6, assists = 18, fouls = 10, minutes = 0),
            PlayerStatsEntity(playerName = "GUILLEM MAROTO DAVID", matchId = "", teamName = club.clubName, points = 171, rebounds = 1, assists = 19, fouls = 9, minutes = 0)
        )
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatCard("PARTIDOS", teamStats["partidos"].toString(), Color(0xFF1E3A8A))
            StatCard("VICTORIAS", teamStats["victorias"].toString(), Color(0xFF10B981))
            StatCard("DERROTAS", teamStats["derrotas"].toString(), Color(0xFFEF4444))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("PF", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(teamStats["pf"].toString(), fontSize = 14.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("PF/P", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(teamStats["pfp"].toString(), fontSize = 14.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("PC", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(teamStats["pc"].toString(), fontSize = 14.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("PC/P", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(teamStats["pcp"].toString(), fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "TIROS DEL EQUIPO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircularProgressChart("T1", 52, 103, 308, Color(0xFFEF4444))
            CircularProgressChart("T2", 100, 254, 254, Color(0xFF10B981))
            CircularProgressChart("T3", 100, 56, 56, Color(0xFF10B981))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "JUGADORES/AS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A8A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E3A8A))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("JUGADOR/A", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
            Text("PJ", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("PT", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("MIN", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            Text("MIN/P", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
        }

        playerStats.forEach { player ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(player.playerName, fontSize = 10.sp, modifier = Modifier.weight(2f))
                Text(player.fouls.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.points.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.rebounds.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
                Text(player.assists.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ClubCalendarContent(club: ClubEntity) {
    var selectedMonth by remember { mutableStateOf("NOVIEMBRE") }

    val matches = remember {
        listOf(
            MatchEntity(
                id = "1",
                homeTeam = "BODEGAS VANACLOIG CEB LLIRIA",
                awayTeam = "CRISCOLOR C.B.C MANISES-QUART",
                homeScore = 88,
                awayScore = 72,
                matchDate = "02/11/2025",
                status = "EN VIVO",
                venue = "PAB MUNI PLA ARC AVDA. PLA DE L'ARC, S/N, LLIRIA (46160)",
                quarter = "4T",
                timeRemaining = "12:30"
            ),
            MatchEntity(
                id = "2",
                homeTeam = "CRISCOLOR C.B.C MANISES-QUART",
                awayTeam = "PICKEN CLARET A",
                homeScore = 96,
                awayScore = 87,
                matchDate = "09/11/2025",
                status = "EN VIVO",
                venue = "PAB MUNI ALBERTO ARNAL ANDRÉS C/REVERENDO FRANCISCO CASTELLÓ GIL 10, MANISES (46940)",
                quarter = "J10",
                timeRemaining = "12:15h"
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

        // Lista de partidos
        matches.forEach { match ->
            MatchCard(match = match)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MatchCard(match: MatchEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFFF6B35), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🏀", fontSize = 20.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Text(
                            text = match.homeScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " - ",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = match.awayScore.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${match.quarter} ${match.timeRemaining}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFF1E3A8A), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚡", fontSize = 20.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = match.homeTeam,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = match.awayTeam,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = match.matchDate,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = Color.Red,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = match.status,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Junior Masculino Nivel Autonómico",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = "Fase Regular",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color(0xFF1E3A8A),
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFF1E3A8A), CircleShape)
                        .padding(8.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Pause",
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFF1E3A8A), CircleShape)
                        .padding(8.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Venue
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = Color(0xFF1E3A8A),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = match.venue ?: "",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun CircularProgressChart(
    title: String,
    percentage: Int,
    made: Int,
    total: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(60.dp)
        ) {
            Canvas(modifier = Modifier.size(60.dp)) {
                val strokeWidth = 8.dp.toPx()
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.3f),
                    style = Stroke(width = strokeWidth)
                )
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = (percentage / 100f) * 360f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$percentage%",
                    fontSize = 8.sp
                )
            }
        }
        Text(
            text = "$made/$total",
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(6.dp),
        color = if (isSelected) Color(0xFF1E3A8A) else Color(0xFFE5E7EB)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun ContactInfo(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1E3A8A),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}