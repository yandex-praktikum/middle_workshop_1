package yandex.practicum.workshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yandex.practicum.workshop.data.Item
import yandex.practicum.workshop.domain.GetItemsUseCase
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {
    private val _refreshState = MutableStateFlow(false)
    val refreshState = _refreshState.asStateFlow()
    private val _items = MutableStateFlow<List<Item>>(listOf())
    val items = _items.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _refreshState.value = true
            getItemsUseCase().collect {
                _items.value = it
                _refreshState.value = false
            }
        }
    }
}