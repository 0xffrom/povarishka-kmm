package romanyuk.povarishka.di

import org.koin.dsl.module
import romanyuk.povarishka.database.DatabaseDriverFactory

actual val platformModule = module { single { DatabaseDriverFactory() } }
