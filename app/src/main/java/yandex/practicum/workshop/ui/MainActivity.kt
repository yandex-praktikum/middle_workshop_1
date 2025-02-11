package yandex.practicum.workshop.ui


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import yandex.practicum.workshop.domain.GetItemsUseCase
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel: ItemListViewModel by viewModels()
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            MaterialTheme {
//                ItemListScreen(viewModel)
//            }
//        }
    }
}

interface NetworkService
class NetworkServiceImpl @Inject constructor(): NetworkService
interface Repository
class RepositoryImpl @Inject constructor(private val networkService: NetworkService) : Repository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

//    @Provides
//    fun provideSomeClassWithBuilder(): SomeClass {
//        return SomeClass.Builder().build()
//    }
    @Binds
    abstract fun bindRepo(repositoryImpl: RepositoryImpl): Repository
}
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    abstract fun bindNetworkService(networkServiceImpl: NetworkServiceImpl): NetworkService
}
class UseCase @Inject constructor(private val repository: Repository)

class PermissionManager @Inject constructor(@ActivityContext private val context: Context)

@HiltViewModel
class MyViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel()
