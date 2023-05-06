package lynx.internship.tv.data.auth


import android.content.ContentValues
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import lynx.internship.tv.domain.auth.AuthRepository
import lynx.internship.tv.domain.auth.AuthResponseListener
import lynx.internship.tv.ui.MainActivity
import lynx.internship.tv.ui.auth.LoginActivity
import java.lang.Exception

class AuthRepositoryImpl : AuthRepository {
    var auth = FirebaseAuth.getInstance()
    override fun login(email: String, password: String,authResponseListener: AuthResponseListener) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                Log.i("xxx","${task.isSuccessful}")
                if(task.isSuccessful == true){
                    authResponseListener.onSuccess(true)
                }else{
                    authResponseListener.onError(Exception(task.exception?.message))
                }
            }
    }
    override fun register(
        email: String,
        password: String,
        authResponseListener: AuthResponseListener
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    authResponseListener.onSuccess(true)

                } else {
                    authResponseListener.onError(Exception(task.exception?.message))
                }
            }
    }

    override fun logOut() {
        auth.signOut()
    }

    override fun isAuthenticated(authResponseListener: AuthResponseListener) {
        val currentUser = auth.currentUser
        val email = currentUser?.email
        Log.d(ContentValues.TAG, "User is logged in with email $email")
        if(currentUser != null){
            authResponseListener.onSuccess(true)
        }else{
            authResponseListener.onSuccess(false)
        }
    }
}