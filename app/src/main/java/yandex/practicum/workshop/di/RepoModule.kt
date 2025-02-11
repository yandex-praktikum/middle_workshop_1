package yandex.practicum.workshop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import yandex.practicum.workshop.data.ItemRepository
import yandex.practicum.workshop.data.ItemRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repositoryImpl: ItemRepositoryImpl): ItemRepository
}