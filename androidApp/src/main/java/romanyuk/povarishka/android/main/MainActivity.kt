package romanyuk.povarishka.android.main

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.coroutineScope
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import org.koin.android.ext.android.inject
import romanyuk.povarishka.android.R.string as strings
import romanyuk.povarishka.common.Resource
import romanyuk.povarishka.recipes.RecipesViewModel
import romanyuk.povarishka.recipes.dto.RecipeDetails

class MainActivity : ComponentActivity() {

    val viewModel: RecipesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.loadMore()
        }

        setContent { MainScreen() }
    }

    @Composable
    fun MainScreen() {
        LazyColumn(
            content = {
                item {
                    val recipeState by
                        viewModel.recipeResource.collectAsState(
                            initial = Resource.Loading(),
                            lifecycle.coroutineScope.coroutineContext
                        )

                    Column(
                        modifier = Modifier.padding(all = 16.dp).fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (val state = recipeState) {
                            is Resource.Data -> RecipeDetails(state.data)
                            is Resource.Loading -> CircularProgressIndicator()
                            is Resource.Error -> ErrorScreen(state.throwable)
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun ErrorScreen(throwable: Throwable) {
        Text(throwable.message.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::loadMore) {
            Text(getString(strings.button_reload), modifier = Modifier.fillMaxWidth())
        }
    }

    @Composable
    fun RecipeDetails(recipe: RecipeDetails) {
        Image(
            painter =
                rememberImagePainter(
                    data = recipe.image,
                    builder = {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    },
                ),
            contentDescription = null,
            modifier = Modifier.size(240.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = recipe.title, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        HtmlText(html = recipe.summary)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::loadMore, modifier = Modifier.fillMaxWidth()) {
            Text(getString(strings.button_load))
        }
    }

    @Composable
    fun HtmlText(html: String, modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}
