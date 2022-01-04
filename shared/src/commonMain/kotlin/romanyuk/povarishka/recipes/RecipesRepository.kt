package romanyuk.povarishka.recipes

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import romanyuk.povarishka.database.Db
import romanyuk.povarishka.recipes.api.RecipeDetailsApi
import romanyuk.povarishka.recipes.dto.RecipeDetails

class RecipesRepository(private val api: RecipeDetailsApi, db: Db) : KoinComponent {

    private val recipeTable = db.database.recipeDetailsTableQueries

    fun obverseRandomRecipe() = flow { emit(api.getRandomRecipes().recipes.first()) }.andCache()

    // Not used, for future :(
    fun getCachedRecipes() = recipeTable.selectAllRecipes().asFlow()

    private fun Flow<RecipeDetails>.andCache() = onEach { recipe ->
        recipeTable.insertRecipeDetails(
            recipe.id,
            recipe.image,
            recipe.imageType,
            recipe.title,
            recipe.summary
        )
    }
}
