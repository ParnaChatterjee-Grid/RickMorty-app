package com.example.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.common.ResultState
import com.example.domain.models.CharacterDetails
import com.example.domain.models.Episode
import com.example.domain.models.Locations
import com.example.domain.models.Origins
import com.example.presentation.R
import com.example.presentation.themes.color
import com.example.presentation.themes.dimens
import com.example.presentation.viewmodels.CharacterDetailsViewModel

@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailsViewModel,
    id: String
)
{
   LaunchedEffect(id)
   {
     viewModel.getCharacterDetails(id)
   }
    //viewModel.getCharacterDetails(id)
   // android.util.Log.d("CharacterDetail","id"+id)
    val characterState = viewModel.charactersState.collectAsStateWithLifecycle()
    CharacterDetails(characterState)
}
@Composable
private fun CharacterDetails(characterState: State<ResultState<CharacterDetails?>>){
   Column(
       modifier = Modifier.fillMaxWidth(1f)
           .padding(MaterialTheme.dimens.smallPadding),
       verticalArrangement = Arrangement.SpaceBetween
   ){
       Box(modifier = Modifier
           .fillMaxWidth()
           .background(MaterialTheme.color.white)){
           when (val state = characterState.value){
               is ResultState.Loading ->{
                   CircularProgressIndicator(Modifier.align(Alignment.Center))
               }

               is ResultState.Error -> {
                   state.exception.localizedMessage?.let {
                       Text(
                           text = it,
                           color = MaterialTheme.colorScheme.error,
                           modifier = Modifier.align(Alignment.Center)
                       )
                   }
               }
               is ResultState.Success -> {
                  ShowCharacterDetails(state.data)
               }
           }
       }
   }
}

@Composable
private fun ShowCharacterDetails(data: CharacterDetails?)
{//for topbar
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.app_bar_height)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.color.topbar_box)
        )
        {
            Text(
                text = data?.name ?: "CharacterDetails",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.color.black,
                modifier = Modifier.align(Alignment.Center)
            )
        }//for Character Image and details
        Column(Modifier.verticalScroll(rememberScrollState()))
        {
            if (data != null) {
                AsyncImage(
                    model = data.image,
                    contentDescription = data.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)

                )
            }
            data?.let { DisplayStatus(it) }
            data?.let { DisplaySpices(it) }
            data?.origin?.let { DisplayOrigin(it) }
            data?.locations?.let { DisplayLocation(it) }
                DisplayEpisodes(data?.episodes)
        }
    }
}
@Composable
fun DisplayStatus(data:CharacterDetails){
    Spacer(Modifier.padding(top = MaterialTheme.dimens.largePadding))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.color.white)
            .padding(MaterialTheme.dimens.mediumPadding)
    ) {
        if (data != null) {
            Text(
                text = data.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.color.black,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.dimens.smallPadding
                )
            )
        }
    }
    //status
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.status),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = data?.status.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }

}
@Composable
fun DisplaySpices(data:CharacterDetails){
    //Species
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.species),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = data?.species.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }

    //Gender
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.gender),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = data?.gender.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }
}
@Composable
fun DisplayOrigin(origin:Origins){
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding,
            top = MaterialTheme.dimens.mediumPadding
        )
    ) {
        Text(
            text = stringResource(R.string.origin),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.color.black
        )
    }
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.origin_name),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = origin?.name.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.origin_dimension),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = origin?.dimension.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }

}
@Composable
fun DisplayLocation(data : Locations){
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding,
            top = MaterialTheme.dimens.mediumPadding
        )
    ) {
        Text(
            text = stringResource(R.string.location),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.color.black
        )
    }
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.location_name),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = data?.name.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding
        )
    ) {
        Text(
            text = stringResource(R.string.location_dimension),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.black
        )
        Text(
            text = data?.dimension.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.color.purple_700
        )
    }

}
@Composable
fun DisplayEpisodes(episodes: List<Episode>?)
{
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.dimens.smallPadding,
            top = MaterialTheme.dimens.mediumPadding
        )
    ) {
        Text(
            text = stringResource(R.string.episodes),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.color.black
        )
    }
    Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.mediumPadding))
    LazyRow(modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = MaterialTheme.dimens.mediumPadding),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.mediumPadding))
    {
        episodes?.size?.let {
            items(
                it
            ){
                index -> EpisodesItem(episodes[index])
            }
        }

    }

}
@Composable
fun EpisodesItem(episode: Episode)
{
    Card(
        modifier = Modifier
            .padding(MaterialTheme.dimens.smallPadding)
            .width(MaterialTheme.dimens.cardWidth)
            .height(MaterialTheme.dimens.cardWidth)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(MaterialTheme.dimens.smallPadding)

        ) {
            Text(
                text = episode.name,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                textAlign = Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}







