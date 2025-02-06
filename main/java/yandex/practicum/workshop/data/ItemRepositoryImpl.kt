package yandex.practicum.workshop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.min

class ItemRepositoryImpl @Inject constructor() : ItemRepository {
    private val _items = List(39) { Item(id = it, name = "Item $it") }

    override fun getItems(page: Int, perPage: Int): Flow<List<Item>> = flow {
        delay(2000L)
        emit(_items.subList(page * perPage, min((page + 1) * perPage, _items.size)))
    }.flowOn(Dispatchers.IO)
}