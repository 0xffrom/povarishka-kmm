package romanyuk.povarishka.network

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

class NetworkClient : KoinComponent {

    val spoonacularLog = Logger(config = StaticConfig(), tag = "[Spoonacular] ")

    val spoonacularClient =
        HttpClient(CIO) {
            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }

                serializer = KotlinxSerializer(json)
            }
            install(Logging) {
                logger =
                    object : io.ktor.client.features.logging.Logger {
                        override fun log(message: String) = spoonacularLog.v(message)
                    }

                level = LogLevel.ALL
            }
            defaultRequest { parameter("apiKey", "9e1c537fff0c4f538651592b21348eed") }
        }
}
