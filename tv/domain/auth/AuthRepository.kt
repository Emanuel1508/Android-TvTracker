package lynx.internship.tv.domain.auth

interface AuthRepository {
    fun login(email: String, password : String,authResponseListener: AuthResponseListener)
    fun register(email: String,password: String,authResponseListener: AuthResponseListener)
    fun logOut()
    fun isAuthenticated(authResponseListener: AuthResponseListener)
}