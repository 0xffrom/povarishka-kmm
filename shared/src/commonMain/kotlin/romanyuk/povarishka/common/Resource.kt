package romanyuk.povarishka.common

sealed class Resource<T> {

    data class Error<T>(val throwable: Throwable) : Resource<T>()
    data class Data<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
}
