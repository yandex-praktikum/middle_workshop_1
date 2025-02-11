package yandex.practicum.workshop.domain

import yandex.practicum.workshop.data.Item
import yandex.practicum.workshop.data.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    operator fun invoke(page: Int, perPage: Int): Flow<List<Item>> = repository.getItems(page, perPage)
}