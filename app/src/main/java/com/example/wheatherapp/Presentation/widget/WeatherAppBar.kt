package com.example.wheatherapp.presentation.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheatherapp.presentation.navigation.WeatherScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    iconColor: Color = Color.Black,
    isFavorite: Boolean = false,
    isMainScreen: Boolean = false,
    isDarkTheme: Boolean = false,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {},
    onThemeToggle: (Boolean) -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val expanded = remember { mutableStateOf(false) }

    ShowDropDownMenu(
        expanded = expanded.value,
        onDismiss = { expanded.value = false },
        onNavigate = onNavigate
    )

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        scrollBehavior = scrollBehavior,

        navigationIcon = {
            icon?.let {
                IconButton(onClick = onButtonClicked) {
                    Icon(it,
                        contentDescription = "",
                        tint = iconColor,
                    )
                }
            }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onThemeToggle(!isDarkTheme) }) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Theme Toggle"
                    )
                }

                IconButton(onClick = { onFavoriteToggle() }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = if (isFavorite) Color.Red.copy(alpha = 0.8f) else Color.Gray
                    )
                }

                IconButton(onClick = { onAddActionClicked() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
                IconButton(onClick = { expanded.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Icon")
                }
            }
        }
    )
}

@Composable
fun ShowDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onNavigate: (String) -> Unit
) {

    val items = listOf("Favorite", "Setting", "About")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 95.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
            modifier = Modifier.width(160.dp)
        ) {

            items.forEach { item ->

                DropdownMenuItem(
                    text = { Text(text = item) },
                    leadingIcon = {
                        Icon(
                            imageVector = when (item) {
                                "About" -> Icons.Default.Info
                                "Favorite" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onDismiss()

                        when (item) {
                            "About" -> onNavigate(WeatherScreens.AboutScreen.name)
                            "Setting" -> onNavigate(WeatherScreens.SettingScreen.name)
                            "Favorite" -> onNavigate(WeatherScreens.FavoriteScreen.name)
                        }
                    }
                )
            }
        }
    }
}


