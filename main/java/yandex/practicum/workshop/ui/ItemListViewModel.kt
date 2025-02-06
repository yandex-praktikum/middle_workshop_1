package yandex.practicum.workshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import yandex.practicum.workshop.data.Item
import yandex.practicum.workshop.domain.GetItemsUseCase
import javax.inject.Inject

sealed class State {
    data object Loading : State()
    data class Data(val page: Int, val items: List<Item>) : State()
}

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState

    init {
        viewModelScope.launch {
            getItemsUseCase().collect { items ->
               _uiState.value = State.Data(0, items)
            }
        }
    }
}