package me.mrSajal.ktornotesclient.android.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.mrSajal.ktornotesclient.android.common.util.Routes
import me.mrSajal.ktornotesclient.android.home.components.NoteItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    homeRefreshState: HomeRefreshState,
    onUiAction: (HomeUiAction) -> Unit,
    onAddNoteClick: () -> Unit,
    navController: NavController,
    fetchNote:()->Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeRefreshState.isRefreshing,
        onRefresh = { onUiAction(HomeUiAction.RefreshAction) },
    )
    LaunchedEffect(Unit) {
        fetchNote()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(homeUiState.notes, key = { notes -> notes.noteId }) {
                NoteItem(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.Notes.route + "?noteId=${it.noteId}")
                        },
                    note = it,
                    onDeleteClick = { onUiAction(HomeUiAction.DeleteNoteAction(it.noteId)) }
                )
            }
        }
        PullRefreshIndicator(
            refreshing = homeRefreshState.isRefreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )

        FloatingActionButton(
            onClick = onAddNoteClick,
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Note")
        }
    }
}
