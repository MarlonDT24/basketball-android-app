package com.matosa.basketallapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matosa.basketallapp.data.CompetitionEntity
import com.matosa.basketallapp.data.TeamEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompetitionsScreen(navController: NavController) {
    var selectedCompetition by remember { mutableStateOf<CompetitionEntity?>(null) }
    var showDropdown by remember { mutableStateOf(false) }

    // Datos mock de competiciones
    val competitions = remember {
        listOf(
            CompetitionEntity(1, "FBCV (SENIOR Y JUNIOR) + FAP JUNIOR", "Senior/Junior"),
            CompetitionEntity(2, "IR (CADETE, INFANTIL Y MINI) + FAP IR", "Cadete/Infantil"),
            CompetitionEntity(3, "NP (AYTOS. VALENCIA - CASTELLÓN + F. PROVINCIAL)", "Provincial"),
            CompetitionEntity(4, "LLIGA VALENCIANA", "Liga Valenciana")
        )
    }

    // Datos mock de equipos para la clasificación
    val teams = remember {
        listOf(
            TeamEntity(1, "BODEGAS VINALOPO CER LLIRIA", 1, 11, 8, 3, 850, 780, 1),
            TeamEntity(2, "ESCRITORES ALZIRA", 1, 11, 7, 4, 820, 795, 2),
            TeamEntity(3, "CREVILLOR C.B.C MANISES-QUART", 1, 11, 6, 5, 800, 790, 3),
            TeamEntity(4, "CB ALGEMESÍ", 1, 10, 5, 5, 750, 760, 4),
            TeamEntity(5, "PICHEN CLARET A", 1, 11, 4, 7, 720, 810, 5),
            TeamEntity(6, "VALENCIA BASKET B", 1, 10, 3, 7, 680, 820, 6)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Competiciones",
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
                text = "Selecciona el tipo, la competición, la categoría y la fase y grupo para acceder a sus datos",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ícono trofeo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, RoundedCornerShape(40.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏆",
                    fontSize = 40.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de tipo
            Text(
                text = "TIPO",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Dropdown para competiciones
            Box {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDropdown = !showDropdown },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedCompetition?.competitionName ?: "Selecciona una competición",
                            fontSize = 14.sp,
                            color = if (selectedCompetition != null) Color.Black else Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Expandir",
                            tint = Color.Gray
                        )
                    }
                }

                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    competitions.forEach { competition ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = competition.competitionName,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                selectedCompetition = competition
                                showDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar clasificación si hay competición seleccionada
            selectedCompetition?.let { competition ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "CLASIFICACIÓN - ${competition.competitionName}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A8A),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Cabecera de la tabla
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("POS", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                            Text("EQUIPO", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(3f))
                            Text("PJ", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                            Text("PG", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                            Text("PP", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(0.8f))
                            Text("PF", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text("PC", fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        }

                        Divider(
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Lista de equipos
                        LazyColumn {
                            items(teams.sortedBy { it.position }) { team ->
                                TeamRow(team = team)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamRow(team: TeamEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Posición
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = when (team.position) {
                        1 -> Color(0xFFFFD700) // Oro
                        2 -> Color(0xFFC0C0C0) // Plata
                        3 -> Color(0xFFCD7F32) // Bronce
                        else -> Color.Gray
                    },
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = team.position.toString(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Nombre del equipo
        Text(
            text = team.teamName,
            fontSize = 11.sp,
            modifier = Modifier.weight(3f),
            color = Color.Black
        )

        // Estadísticas
        Text(text = team.gamesPlayed.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.8f))
        Text(text = team.wins.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.8f))
        Text(text = team.losses.toString(), fontSize = 10.sp, modifier = Modifier.weight(0.8f))
        Text(text = team.pointsFor.toString(), fontSize = 10.sp, modifier = Modifier.weight(1f))
        Text(text = team.pointsAgainst.toString(), fontSize = 10.sp, modifier = Modifier.weight(1f))
    }
}