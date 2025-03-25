package yandex.practicum.workshop.domain

import kotlinx.coroutines.flow.Flow
import yandex.practicum.workshop.data.Item
import yandex.practicum.workshop.data.ItemRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    operator fun invoke(): Flow<List<Item>> = repository.getItems()
}