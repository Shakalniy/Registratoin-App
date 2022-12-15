package com.example.questionnaire

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.questionnaire.databinding.ActivityAccountBinding
import com.example.questionnaire.databinding.ActivityMainBinding
import com.parse.ParseException
import com.parse.ParseUser

class Account : AppCompatActivity() {

    lateinit var bindingClass: ActivityAccountBinding
    var logout: Button? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        logout = bindingClass.logout
        progressDialog = ProgressDialog(this)

        logout?.setOnClickListener {
            progressDialog!!.show()

            ParseUser.logOutInBackground { e: ParseException? ->
                progressDialog!!.dismiss()
                if (e == null)
                    showAlert("So, you're going...", "Ok...Bye-bye then")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val ok = builder.create()
        ok.show()
    }
}