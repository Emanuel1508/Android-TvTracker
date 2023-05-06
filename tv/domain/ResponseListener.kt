package lynx.internship.tv.domain


interface ResponseListener<T> {

    fun onSuccess(value : T)

    fun onError(error: Exception)
}