package com.example.myfoodera

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.username)
        val pass = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.button2)
        val googleBtn = findViewById<Button>(R.id.gSignInBtn)
        val registerTxt = findViewById<TextView>(R.id.textView7)

        // ✅ SIMPLE EMAIL LOGIN (No Firebase for now)
        loginBtn.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = pass.text.toString().trim()

            if (emailText.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simple login - accept any credentials
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_NAME", emailText.split("@")[0]) // Use part before @ as name
                putExtra("USER_EMAIL", emailText)
            }
            startActivity(intent)
            finish()
        }

        // ✅ OPEN REGISTER PAGE
        registerTxt.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // ✅ CONFIGURE GOOGLE SIGN-IN
        configureGoogleSignIn()

        // ✅ GOOGLE SIGN-IN BUTTON
        googleBtn.setOnClickListener {
            signInWithGoogle()
        }

        // ✅ SKIP LOGIN / CONTINUE AS GUEST
        val skipLoginBtn = findViewById<Button>(R.id.skipLoginBtn)
        skipLoginBtn?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_NAME", "Guest User")
                putExtra("IS_GUEST", true)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun configureGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Make sure this is in strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        if (account == null) {
            Toast.makeText(this, "Google account not found", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = auth.currentUser
                    val userName = user?.displayName ?: account.displayName ?: "Google User"
                    val userEmail = user?.email ?: account.email ?: ""

                    Toast.makeText(this, "Google Sign-In successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomeActivity::class.java).apply {
                        putExtra("USER_NAME", userName)
                        putExtra("USER_EMAIL", userEmail)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        // Check if user is already signed in with Google
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            // User is already signed in with Google
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_NAME", account.displayName ?: "Google User")
                putExtra("USER_EMAIL", account.email ?: "")
            }
            startActivity(intent)
            finish()
        }

        // Check if user is signed in with Firebase
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("USER_NAME", currentUser.displayName ?: currentUser.email?.split("@")?.get(0) ?: "User")
                putExtra("USER_EMAIL", currentUser.email ?: "")
            }
            startActivity(intent)
            finish()
        }
    }
}