package yandex.practicum.workshop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(viewModel: ItemListViewModel) {
    val items by viewModel.items.collectAsState()
    val isRefreshing by viewModel.refreshState.collectAsState()

    val pullToRefreshState = rememberPullToRefreshState()
    Column(
        Modifier.pullToRefresh(
            isRefreshing = isRefreshing,
            state = pullToRefreshState,
            onRefresh = { viewModel.loadItems() }),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height((64 * pullToRefreshState.distanceFraction).dp),
            contentAlignment = Alignment.Center

        ) {
            if (isRefreshing)
                Text("Загрузка...", style = MaterialTheme.typography.headlineMedium)
            else if (pullToRefreshState.distanceFraction >= 1)
                Text("Отпустите для обновления", style = MaterialTheme.typography.headlineMedium)
        }

//    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = { viewModel.loadItems() }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item -> Item(item = item) }
        }
    }
}
