package com.matosa.basketallapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val bottomNavController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🏀",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "FBCV",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A8A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
                onNavigateToClubs = { navController.navigate("clubs") },
                onNavigateToCompetitions = { navController.navigate("competitions") },
                onNavigateToLiveMatches = { navController.navigate("live_matches") }
            )
            1 -> FavoritesContent(modifier = Modifier.padding(paddingValues))
            2 -> NotificationsContent(modifier = Modifier.padding(paddingValues))
            3 -> MenuContent(modifier = Modifier.padding(paddingValues))
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
        BottomNavItem("Favoritos", Icons.Default.Star),
        BottomNavItem("Alertas", Icons.Default.Notifications),
        BottomNavItem("Menú", Icons.Default.Menu)
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botón Clubes
        MainMenuButton(
            icon = "🏛️",
            title = "CLUBES",
            backgroundColor = Color(0xFFE5E7EB),
            onClick = onNavigateToClubs
        )

        // Botón Competiciones
        MainMenuButton(
            icon = "🏆",
            title = "COMPETICIONES",
            backgroundColor = Color(0xFFFF6B35),
            onClick = onNavigateToCompetitions
        )

        // Botón Partidos en Directo
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

// Contenido temporal para las otras pestañas
@Composable
fun FavoritesContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Favoritos - Próximamente")
    }
}

@Composable
fun NotificationsContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Notificaciones - Próximamente")
    }
}

@Composable
fun MenuContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Menú - Próximamente")
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)