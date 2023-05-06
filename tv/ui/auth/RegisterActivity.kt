package lynx.internship.tv.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import lynx.internship.tv.ui.MainActivity
import lynx.internship.tv.R
import lynx.internship.tv.databinding.ActivityRegisterBinding

import lynx.internship.tv.domain.auth.AuthResponseListener
import lynx.internship.tv.domain.TvTrackerFactory


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonRegister.setOnClickListener {
            Registration()
        }
    }

    private fun Registration() {
        val mustcompleteall: String = getString(R.string.mustcompleteallfields)
        val passnomatch: String = getString(R.string.passnotmatch)
        val regsucces: String = getString(R.string.rsuccesful)
        val regfailed: String = getString(R.string.rfailed)
        val ok: String = getString(R.string.ok)

        when {
            !isRegisterValid() -> Toast.makeText(
                this,
                mustcompleteall,
                Toast.LENGTH_SHORT
            ).show()
            !isPasswordTheSame() -> Toast.makeText(
                this,
                passnomatch,
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                val email = binding.etEmailAddress.text.toString()
                val password = binding.etTextPassword.text.toString()
               TvTrackerFactory.createTvTrackerAuthRepository().register(email,password,object :
                   AuthResponseListener {
                   override fun onSuccess(success: Boolean) {
                       if(success == true){
                           Toast.makeText(this@RegisterActivity, regsucces, Toast.LENGTH_SHORT)
                               .show()
                           navigateToMainActivity()
                       }
                   }
                   override fun onError(error: Exception) {
                       val builder = AlertDialog.Builder(this@RegisterActivity)
                       builder.setTitle(regfailed)
                       builder.setMessage(error.message)
                       builder.setPositiveButton(ok, null)
                       val dialog = builder.create()
                       dialog.show()
                   }

               })
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun isRegisterValid(): Boolean {
        return binding.etName.text.isNotBlank() && binding.etEmailAddress.text.isNotBlank() && binding.etTextPassword.text.isNotBlank() && binding.etVerifyPassword.text.isNotBlank()
    }

    private fun isPasswordTheSame(): Boolean {

        return binding.etVerifyPassword.text.toString() == binding.etTextPassword.text.toString()
    }

}
