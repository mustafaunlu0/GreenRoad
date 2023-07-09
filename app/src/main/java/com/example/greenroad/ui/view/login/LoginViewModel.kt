package com.example.greenroad.ui.view.login

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.greenroad.ui.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,

) : ViewModel() {

    private var _isSuccessSignUp = MutableLiveData<Boolean>()
    private var _isSuccessSignIn = MutableLiveData<Boolean>()

    val isSuccessSignUp: LiveData<Boolean>
        get() = _isSuccessSignUp

    val isSuccessSignIn : LiveData<Boolean>
        get() = _isSuccessSignIn

    fun firebaseCurrentUser() = auth.currentUser


    fun signUp(email: String, username: String, pass: String, pass0: String) {

        if (email.isNotEmpty() && username.isNotEmpty() && pass.isNotEmpty() && pass0.isNotEmpty()) {
            if (isValidEmail(email)) {
                if (pass == pass0) {
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            _isSuccessSignUp.value = task.isSuccessful
                        }.addOnFailureListener {
                            println("Hata: $it")
                        }
                } else {
                    println("Şifreler Uyuşmadı")
                }
            } else {
                println("Geçersiz Email")
            }

        }

    }
    fun signIn(email: String,pass : String){
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                _isSuccessSignIn.value = task.isSuccessful

            }.addOnFailureListener {
                println("Hata: $it")

            }


        }
    }




    fun isValidEmail(email: CharSequence): Boolean {
        var isValid = true
        /*
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (!matcher.matches()) {
            isValid = false
        }

         */
        return isValid
    }

}