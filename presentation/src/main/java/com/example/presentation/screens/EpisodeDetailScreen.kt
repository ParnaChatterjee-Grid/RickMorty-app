package com.example.presentation.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.ResultState
import com.example.domain.models.Episode
import com.example.presentation.themes.color
import com.example.presentation.themes.dimens
import com.example.presentation.viewmodels.EpisodeDetailsViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EpisodeDetailScreen(
    viewModel: EpisodeDetailsViewModel,
    modifier: Modifier = Modifier,
    onBackButton: () -> Unit
) {
    val episodeDetailsViewModel = remember { viewModel }
    val episodeState = episodeDetailsViewModel.episodeState.collectAsStateWithLifecycle()
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun EpisodeDetails(
    episodeState: StateFlow<ResultState<Episode?>>,
    modifier: Modifier = Modifier,
    onBackButton: () -> Unit
    ){
    Column(
        modifier.fillMaxWidth(1f),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.color.white))
        {
             when (val state = episodeState.value) {
                is ResultState.Error -> state.exception.localizedMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is ResultState.Loading -> 
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                is ResultState.Success -> {
                    ShowEpisodeDetails(state.data,onBackButton)
                }
            }
        }
    }

}

@Composable
fun ShowEpisodeDetails(data: Episode?, onBackButton: () -> Unit,
                       modifier: Modifier = Modifier) {
    //for topbar
    Column(
        modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.app_bar_height)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.color.topbar_box)
        )
        {
            Icon(
                painter = painterResource(com.example.presentation.R.drawable.ic_back_arrow),
                contentDescription = "Back Button",
                modifier = Modifier
                    .padding(all = MaterialTheme.dimens.mediumPadding)
                    .align(Alignment.CenterStart)
                    .clickable { onBackButton.invoke() }
            )
            Text(
                text = data?.name ?: stringResource(com.example.presentation.R.string.episode_screen),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.color.black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}
