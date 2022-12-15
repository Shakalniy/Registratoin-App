package com.example.questionnaire

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.questionnaire.databinding.ActivityMainBinding
import com.parse.ParseException
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityMainBinding
    private var username: Editable? = null
    private var password: Editable? = null
    private var registrationBtn: Button? = null
    private var loginBtn: Button? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        progressDialog = ProgressDialog(this@MainActivity)
        username = bindingClass.editLogin.text
        password = bindingClass.editPassword.text
        registrationBtn = bindingClass.bRegister
        loginBtn = bindingClass.bLogin

        loginBtn?.setOnClickListener(View.OnClickListener {
            login(username.toString(), password.toString())
        })

        registrationBtn?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, Registration::class.java))
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.run {

        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun login(username: String, password: String) {
        progressDialog?.show()
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, parseException: ParseException? ->
            progressDialog?.dismiss()
            if (parseUser != null) {
                showAlert("Successful Login", "Welcome back " + username + " !")
            } else {
                ParseUser.logOut()
                if (parseException != null) {
                    Toast.makeText(this, parseException.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                val intent = Intent(this, Account::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val ok = builder.create()
        ok.show()
    }
}