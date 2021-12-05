package romanyuk.povarishka.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import romanyuk.povarishka.database.Db
import romanyuk.povarishka.network.NetworkClient
import romanyuk.povarishka.recipes.RecipesRepository
import romanyuk.povarishka.recipes.RecipesViewModel
import romanyuk.povarishka.recipes.api.RecipeDetailsApi

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
    modules(platformModule)
}

// called by iOS etc
fun initKoin() = initKoin {
    val log = Logger(config = StaticConfig(), tag = "KOIN_")
    log.d("initKoin ios")
}

val commonModule = module {
    single { NetworkClient() }
    single { RecipeDetailsApi(get()) }
    single { RecipesRepository(get(), get()) }
    single { RecipesViewModel(get()) }
    single { Db() }
}

expect val platformModule: Module
