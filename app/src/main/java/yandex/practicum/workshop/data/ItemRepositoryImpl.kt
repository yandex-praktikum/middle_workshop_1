package yandex.practicum.workshop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val ITEMS_COUNT = 43

class ItemRepositoryImpl @Inject constructor() : ItemRepository {
    private var startItemId = 0

    override fun getItems(): Flow<List<Item>> = flow {
        delay(2000L)
        emit(List(ITEMS_COUNT) { Item(id = it, name = "Item ${startItemId + it}") })
        startItemId += ITEMS_COUNT
    }.flowOn(Dispatchers.IO)
}