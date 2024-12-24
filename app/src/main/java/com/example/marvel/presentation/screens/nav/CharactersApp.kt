package com.example.marvel.presentation.screens.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.R
import com.example.marvel.presentation.screens.chardetails.CharacterDetails
import com.example.marvel.presentation.screens.characters.CharactersList
import com.example.marvel.presentation.screens.characters.CharactersViewModel

@Composable
fun CharactersApp(
    charactersViewModel: CharactersViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    Scaffold(
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "CharacterList",
            modifier = Modifier
                .padding(padding)
                .background(color = colorResource(id = R.color.color_bg))
        ) {
            composable("CharacterList") {
                CharactersList(
                    charactersViewModel = charactersViewModel,
                    navController = navController
                )
            }
            composable(
                route = "charDetails/{characterId}",
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) {
                val characterId = it.arguments?.getInt("characterId") ?: return@composable
                CharacterDetails(
                    charactersViewModel = charactersViewModel,
                    characterId = characterId,
                    navController = navController
                )
            }
        }
    }
}