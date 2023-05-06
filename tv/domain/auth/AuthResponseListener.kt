package lynx.internship.tv.domain.auth

interface AuthResponseListener {

    fun onSuccess(success : Boolean)
    fun onError(error: Exception)
}