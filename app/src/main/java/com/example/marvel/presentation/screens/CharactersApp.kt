package com.example.marvel.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.presentation.components.details.CharacterDetails
import com.example.marvel.presentation.components.list.CharactersList

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
            modifier = Modifier.padding(padding)
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