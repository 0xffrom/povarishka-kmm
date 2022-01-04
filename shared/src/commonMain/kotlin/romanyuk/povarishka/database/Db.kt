package romanyuk.povarishka.database

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import romanyuk.povarishka.db.AppDatabase

class Db: KoinComponent {
    private val databaseDriverFactory: DatabaseDriverFactory by inject()

    val database = AppDatabase(databaseDriverFactory.createDriver())
}