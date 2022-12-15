package com.example.questionnaire

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.example.questionnaire.databinding.ActivityRegistrationBinding
import com.parse.ParseUser
import com.parse.SignUpCallback


class Registration : AppCompatActivity() {

    lateinit var bindingClass: ActivityRegistrationBinding
    private var username: Editable? = null
    private var email: Editable? = null
    private var password: Editable? = null
    private var passwordagain: Editable? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val signup = bindingClass.bReg
        username = bindingClass.editLogin.text
        email = bindingClass.editEmail.text
        password = bindingClass.editPassword.text
        passwordagain = bindingClass.editPasswordAgain.text
        progressDialog = ProgressDialog(this)

        signup!!.setOnClickListener {
            if (!TextUtils.isEmpty(username?.toString()) && password?.toString() == passwordagain?.toString()) {
                signUp(username.toString(), password.toString(), email.toString());
            }
            else {
                Toast.makeText(
                    this,
                    "Make sure that the values you entered are correct.",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.run {

        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun signUp(username: String, password: String, email: String) {
        progressDialog?.show()
        val user = ParseUser()
        user.setUsername(username)
        user.setPassword(password)
        user.setEmail(email)
        user.signUpInBackground(SignUpCallback() {
            progressDialog?.dismiss()
            if (it == null) {
                showAlert("Successful Sign Up!", "Welcome " + username + " !");
            } else {
                ParseUser.logOut();
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show();
            }
        });
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