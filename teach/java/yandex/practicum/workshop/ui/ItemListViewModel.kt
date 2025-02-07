package yandex.practicum.workshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yandex.practicum.workshop.data.Item
import yandex.practicum.workshop.domain.GetItemsUseCase
import javax.inject.Inject

enum class PaginationState {
    IDLE, LOADING
}

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {
    private val _paginationState = MutableStateFlow(PaginationState.IDLE)
    val paginationState = _paginationState.asStateFlow()
    private val _items = MutableStateFlow<List<Item>>(listOf())
    val items = _items.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _paginationState.value = PaginationState.LOADING

            getItemsUseCase(0, 99).collect(::processNewItems)
        }
    }

    private fun processNewItems(newItems: List<Item>) {
        _items.update { it + newItems }
        _paginationState.value = PaginationState.IDLE
    }
}