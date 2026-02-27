package com.example.wheatherapp.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wheatherapp.navigation.WeatherScreens
import com.example.wheatherapp.widget.WeatherAppBara

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WeatherAppBara(
                title = "Search",
                icon = Icons.Filled.ArrowBack,
                isMainScreen = false,
                onButtonClicked = { navController.popBackStack() },
            )
        }
    ) { innerPadding ->
        Surface(modifier = modifier.padding(innerPadding)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar() { it ->
                    navController.navigate(WeatherScreens.MainScreen.name + "/$it")
                }
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}) {
    val searchQuery = rememberSaveable {
        mutableStateOf<String>("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQuery.value) {
        searchQuery.value.trim().isNotEmpty()
    }
    CommonTextField(
        valueState = searchQuery,
        placeHolder = "erbil",
        onAction = KeyboardActions {
            if (!valid) return@KeyboardActions
            onSearch(searchQuery.value.trim())
            searchQuery.value = ""
            keyboardController?.hide()
        })
}


@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    type: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        placeholder = { Text(placeHolder) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = type, imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Blue.copy(alpha = 0.4f),
            cursorColor = Color.Black,
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.2f)
        ),

        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
}