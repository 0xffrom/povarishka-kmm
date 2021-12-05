package romanyuk.povarishka.android.app

import android.app.Application
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level.ERROR
import org.koin.core.logger.Level.NONE
import romanyuk.povarishka.di.commonModule
import romanyuk.povarishka.di.initKoin

class PovareshkaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (BuildConfig.DEBUG) ERROR else NONE)
            androidContext(this@PovareshkaApplication)
            modules(commonModule)
        }
    }
}
