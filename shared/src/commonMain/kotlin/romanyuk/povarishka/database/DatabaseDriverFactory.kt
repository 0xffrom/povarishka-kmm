package romanyuk.povarishka.database

import com.squareup.sqldelight.db.SqlDriver
import romanyuk.povarishka.db.AppDatabase

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
