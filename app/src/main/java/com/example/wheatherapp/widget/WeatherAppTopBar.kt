package com.example.wheatherapp.widget

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wheatherapp.model.Favorite
import com.example.wheatherapp.navigation.WeatherScreens
import com.example.wheatherapp.screens.favorite.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBara(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = false,
    isFavorite: Boolean = false,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit,
    onFavoriteToggle: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
// j
    var expanded = remember { mutableStateOf(false) }

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
            if (icon != null) {
                IconButton(onClick = onButtonClicked) {
                    Icon(icon, contentDescription = "Back")
                }
            }
            if (isMainScreen)
                IconButton(
                    onClick = {
                        onFavoriteToggle()
                    }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
        },

        actions = {


            if (isMainScreen) {
                IconButton(onClick = onAddActionClicked) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = { expanded.value = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
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


