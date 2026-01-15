package com.matosa.basketallapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matosa.basketallapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón atrás
                        if (selectedTab != 0) {
                            IconButton(onClick = { selectedTab = 0 }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = Color.White
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp))
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_letra),
                                contentDescription = "FBCV Logo",
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(80.dp)
                            )
                        }

                        // Botón home
                        IconButton(onClick = { selectedTab = 0 }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Inicio",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E3A8A)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("player_profile")
                },
                containerColor = Color(0xFFFF6B35),
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Perfil del jugador",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeContent(
                modifier = Modifier.padding(paddingValues),
                onNavigateToClubs = { selectedTab = 1 },
                onNavigateToCompetitions = { selectedTab = 3 },
                onNavigateToLiveMatches = { selectedTab = 2 }
            )
            1 -> ClubsScreen(modifier = Modifier.padding(paddingValues))
            2 -> LiveMatchesScreen(modifier = Modifier.padding(paddingValues))
            3 -> CompetitionsScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavItem("Inicio", Icons.Default.Home),
        BottomNavItem("Clubes", Icons.Default.Info),
        BottomNavItem("Partidos", Icons.Default.PlayArrow),
        BottomNavItem("Competiciones", Icons.Default.DateRange),
    )

    NavigationBar(
        containerColor = Color(0xFF1E3A8A),
        contentColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.6f)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )
                },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color(0xFF3B82F6)
                )
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigateToClubs: () -> Unit,
    onNavigateToCompetitions: () -> Unit,
    onNavigateToLiveMatches: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainMenuButton(
            icon = "️🏀",
            title = "CLUBES",
            backgroundColor = Color(0xFFE5E7EB),
            onClick = onNavigateToClubs
        )

        Spacer(modifier = Modifier.height(16.dp))

        MainMenuButton(
            icon = "🏆",
            title = "COMPETICIONES",
            backgroundColor = Color(0xFFFF6B35),
            onClick = onNavigateToCompetitions
        )

        Spacer(modifier = Modifier.height(16.dp))

        MainMenuButton(
            icon = "📺",
            title = "PARTIDOS EN DIRECTO",
            backgroundColor = Color(0xFF1E3A8A),
            onClick = onNavigateToLiveMatches
        )
    }
}

@Composable
fun MainMenuButton(
    icon: String,
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = null
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (backgroundColor == Color(0xFFE5E7EB)) Color.Black else Color.White
                )
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)