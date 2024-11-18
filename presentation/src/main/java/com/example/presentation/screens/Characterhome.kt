package com.example.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.domain.CharacterSchema
import com.example.presentation.uitheme.HomeMaterialDiemens


const val  GridCell = 2
@Composable
fun CharacterItems(character: CharacterSchema) {
    val imageUrl = remember(character.image) { character.image }
    val titleText : String = character.name
  Card (
  Modifier.padding(HomeMaterialDiemens.paddingSmall).fillMaxWidth()){
     Box(Modifier.fillMaxSize()){
         AsyncImage(
             modifier = Modifier.fillMaxSize().aspectRatio(1f),
             model = imageUrl,
             contentDescription = titleText
         )
         Text(
             text = titleText,
             modifier = Modifier.wrapContentSize().align(Alignment.BottomCenter),
             color = Color.White
         )
     }
  }

}


    @Composable
    private fun CharacterGrid(characters: List<CharacterSchema>) {

        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = GridCells.Fixed(GridCell),
            contentPadding = PaddingValues(HomeMaterialDiemens.paddingSmall)
        ) {
            items(characters.size) { index ->
                CharacterItems(character = characters[index])
            }
        }
    }


