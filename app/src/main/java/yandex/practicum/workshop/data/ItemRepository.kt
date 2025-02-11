package yandex.practicum.workshop.data

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItems(page: Int, perPage: Int): Flow<List<Item>>
}