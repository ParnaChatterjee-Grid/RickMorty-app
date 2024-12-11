package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.CharacterDetailScreen
import com.example.presentation.screens.CharacterListScreen
import com.example.presentation.viewmodels.CharacterDetailsViewModel
import com.example.presentation.viewmodels.CharacterViewModel

@Composable
fun RMNavGraph(ViewModelprovider: androidx.lifecycle.ViewModelProvider) {
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = RMScreenRoutes.homeScreen) {
    composable(RMScreenRoutes.homeScreen) {
        val characterViewModel: CharacterViewModel =ViewModelprovider.get(CharacterViewModel::class.java)
        CharacterListScreen(characterViewModel,navController)
     }
        composable(RMScreenRoutes.characterDetailScreen+"/{characterid}",
            ) {
            val id = it.arguments?.getString("characterid")
            val characterDetailViewModel: CharacterDetailsViewModel =ViewModelprovider.get(CharacterDetailsViewModel::class.java)
            if (id != null) {
                CharacterDetailScreen(characterDetailViewModel, id,navController)
            }
        }
    }
}