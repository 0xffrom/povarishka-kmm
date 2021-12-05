package romanyuk.povarishka.recipes.api

import io.ktor.client.request.*
import org.koin.core.component.KoinComponent
import romanyuk.povarishka.network.NetworkClient
import romanyuk.povarishka.recipes.dto.RecipeDetailsWrapper

class RecipeDetailsApi(private val networkClient: NetworkClient) : KoinComponent {

    suspend fun getRandomRecipes(): RecipeDetailsWrapper =
        networkClient.spoonacularClient.get("https://api.spoonacular.com/recipes/random")
}
