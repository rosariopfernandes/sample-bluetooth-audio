package io.github.rosariopfernandes.bluetoothingspeaker.userlogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.androidthings.bluetooth.audio.R
import com.google.firebase.auth.FirebaseAuth
import io.github.rosariopfernandes.bluetoothingspeaker.A2dpSinkActivity

class LoginActivity : Activity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val enteredEmail = username.text.toString()
            val enteredPassword = password.text.toString()
            loading.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(enteredEmail, enteredPassword)
                    .addOnSuccessListener {
                        launchMainActivity()
                        loading.visibility = View.GONE
                    }
                    .addOnFailureListener { e ->
                        showLoginFailed(e.message!!)
                        e.printStackTrace()
                        loading.visibility = View.GONE
                    }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        currentUser?.let {
            launchMainActivity()
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(this@LoginActivity, A2dpSinkActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
