package com.example.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.presentation.screens.CharacterDetailScreen
import com.example.presentation.screens.CharacterListScreen
import com.example.presentation.viewmodels.CharacterDetailsViewModel
import com.example.presentation.viewmodels.CharacterViewModel

@Composable
fun RMNavGraph(viewModelprovider: androidx.lifecycle.ViewModelProvider) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RMScreenRoutes.HomeScreen) {
        composable<RMScreenRoutes.HomeScreen> {
            val characterViewModel: CharacterViewModel =
                viewModelprovider.get(CharacterViewModel::class.java)
            CharacterListScreen(characterViewModel,
                onNavigateTo = { characterId: String ->
                    navController.navigate(RMScreenRoutes.CharacterDetail(characterId))
                })
        }
        composable<RMScreenRoutes.CharacterDetail> { navBackStackEntry ->
            val characterDetails: RMScreenRoutes.CharacterDetail =
                navBackStackEntry.toRoute<RMScreenRoutes.CharacterDetail>()
            val characterDetailViewModel: CharacterDetailsViewModel =
                viewModelprovider[CharacterDetailsViewModel::class.java]

            var id by rememberSaveable { mutableStateOf("") }
            if (!id.contentEquals(characterDetails.characterId)) {
                characterDetailViewModel.getCharacterDetails(characterDetails.characterId)
                id = characterDetails.characterId
            }
            if (characterDetails.characterId != null) {
                CharacterDetailScreen(
                    characterDetailViewModel,
                    onBackButton = navController::navigateUp
                )
            } else {
                ShowNoRecord()
            }
        }
    }
}

@Composable
private fun ShowNoRecord(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(1f))
    {
        Text(
            text = "No Details Present For This Character",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

