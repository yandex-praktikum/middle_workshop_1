package yandex.practicum.workshop.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor() : ItemRepository {
    private val _items = MutableStateFlow(List(100) { Item(id = it, name = "Item $it") })

    override fun getItems(): Flow<List<Item>> = _items

    override fun updateItem(item: Item): Flow<Unit> {
        return flowOf(Unit).onStart {
            _items.update { currentList ->
                currentList.map { if (it.id == item.id) item else it }
            }
        }
    }
}