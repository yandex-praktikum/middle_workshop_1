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
    IDLE, LOADING_FIRST, LOADING_PAGE, NO_MORE
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
        if (canNotLoad())
            return

        viewModelScope.launch {
            _paginationState.value =
                if (pageToLoad == 0) PaginationState.LOADING_FIRST else PaginationState.LOADING_PAGE

            getItemsUseCase(pageToLoad, PER_PAGE).collect(::processNewItems)
        }
    }

    private fun canNotLoad() = paginationState.value in setOf(
        PaginationState.NO_MORE,
        PaginationState.LOADING_PAGE,
        PaginationState.LOADING_FIRST
    )

    private fun processNewItems(newItems: List<Item>) {
        val lastPage = newItems.size < PER_PAGE
        if (!lastPage)
            pageToLoad++

        _items.update { it + newItems }
        _paginationState.value = if (!lastPage) PaginationState.IDLE else PaginationState.NO_MORE
    }

    companion object {
        private const val PER_PAGE = 12
    }
}