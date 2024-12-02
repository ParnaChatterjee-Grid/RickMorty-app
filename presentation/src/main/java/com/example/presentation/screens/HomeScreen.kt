package com.example.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.common.ResultState
import com.example.domain.models.Characters
import com.example.presentation.R
import com.example.presentation.viewmodels.CharacterViewModel


@Composable
fun CharacterListScreen(viewModel: CharacterViewModel) {
    val characterState by viewModel.charactersState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.rick_morty_app),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)

        )
        when (val state = characterState) {
            is ResultState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            is ResultState.Success -> {
                SetCharactersList(state.data.requireNoNulls())
            }
            is ResultState.Error ->{
                state.exception.localizedMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                    )
                }
            }

            else -> {
                Text(
                    text = " No data found ",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                )
            }
        }




    }
}

@Composable
private fun SetCharactersList(characterList: kotlin.collections.List<com.example.domain.models.Characters>) {
    LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characterList.size, key = { characterList[it].id }) { index ->
            SetCharacterItem(character = characterList[index])
        }
    }
}

@Composable
private fun SetCharacterItem(character: Characters) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
            Text(
                text = character.name,
                modifier = Modifier
                    .padding(8.dp)
                    .align(androidx.compose.ui.Alignment.BottomCenter),
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize
            )
        }
    }

}

