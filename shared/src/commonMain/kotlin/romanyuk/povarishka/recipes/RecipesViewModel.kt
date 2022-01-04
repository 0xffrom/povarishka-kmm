package romanyuk.povarishka.recipes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import romanyuk.povarishka.common.Resource
import romanyuk.povarishka.recipes.dto.RecipeDetails

@OptIn(ObsoleteCoroutinesApi::class)
// default coroutine scope for iOS
class RecipesViewModel(private val repository: RecipesRepository) {
    private val scope = MainScope()

    private val channel = BroadcastChannel<Resource<RecipeDetails>>(1)

    val recipeResource: Flow<Resource<RecipeDetails>> = channel.openSubscription().receiveAsFlow()

    fun loadMore() {
        CoroutineScope(scope.coroutineContext).launch {
            channel.send(Resource.Loading())
            try {
                repository.obverseRandomRecipe().collect { channel.send(Resource.Data(it)) }
            } catch (throwable: Throwable) {
                channel.send(Resource.Error(throwable))
            }
        }
    }
}
