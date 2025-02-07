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
    IDLE, LOADING_FIRST, LOADING_PAGE
}

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {
    private var pageToLoad = 0

    private val _paginationState = MutableStateFlow(PaginationState.IDLE)
    val paginationState = _paginationState.asStateFlow()
    private val _items = MutableStateFlow<List<Item>>(listOf())
    val items = _items.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _paginationState.value =
                if (pageToLoad == 0) PaginationState.LOADING_FIRST else PaginationState.LOADING_PAGE

            getItemsUseCase(pageToLoad, PER_PAGE).collect(::processNewItems)
        }
    }

    private fun processNewItems(newItems: List<Item>) {
        pageToLoad++

        _items.update { it + newItems }
        _paginationState.value = PaginationState.IDLE
    }

    companion object {
        private const val PER_PAGE = 12
    }
}