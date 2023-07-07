package com.example.greenroad.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.greenroad.databinding.FragmentSignUpBinding
import com.example.greenroad.util.Constants.animals
import com.example.greenroad.ui.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private var currentPosition = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("716579750984-58fsd5o09rphmuk0tmnos2j0dns47mr3.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

        /*
        val firebaseUser = auth.currentUser;

        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
            startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

         */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.title.setOnClickListener {
            findNavController().navigateUp()
        }

        //Change Image
        _binding!!.nextArrow.setOnClickListener {
            if (currentPosition < animals.size - 1) {
                currentPosition++
                playAnimation(_binding!!.animalImage, currentPosition)
            } else {
                currentPosition = 0
                playAnimation(_binding!!.animalImage, currentPosition)
            }
        }
        _binding!!.backArrow.setOnClickListener {

            if (currentPosition == 0) {
                currentPosition = animals.size - 1
                playAnimation(_binding!!.animalImage, currentPosition)

            } else {
                currentPosition--
                playAnimation(_binding!!.animalImage, currentPosition)
            }
        }

        //Next Step

        _binding!!.nextStepButton.setOnClickListener {
            val username = _binding!!.usernameEditText.text.toString()
            val email = _binding!!.emailEditText.text.toString()
            val pass = _binding!!.passwordEditText.text.toString()
            val passO = _binding!!.passwordOtherEditText.text.toString()

            //&& passO.isNotBlank() && username.isNotBlank()
            if (email.isNotBlank() && pass.isNotBlank()) {

                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "BAŞARILI", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "${task.exception}", Toast.LENGTH_LONG).show()
                        }
                    }

            }
        }

        // clientID: 716579750984-3lsllfk785aep83tds40q3rl3k9ffq9o.apps.googleusercontent.com


        _binding!!.gmailButton.setOnClickListener {
            val intent = googleSignInClient.signInIntent

            startActivityForResult(intent, 100)
        }


    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check condition
        if (requestCode == 100) {

            // When request code is equal to 100 initialize task
            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            // check condition
            if (signInAccountTask.isSuccessful) {
                println("başarıı")
                // When google sign in successful initialize string
                val s = "Google sign in successful"
                // Display Toast
                displayToast(s)
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount = signInAccountTask.getResult(
                        ApiException::class.java
                    )
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential =
                            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                        // Check credential
                        auth.signInWithCredential(authCredential)
                            .addOnCompleteListener(requireActivity(),
                                OnCompleteListener<AuthResult?> { task ->
                                    // Check condition
                                    if (task.isSuccessful) {
                                        // When task is successful redirect to profile activity display Toast
                                        startActivity(
                                            Intent(
                                                requireActivity(),
                                                MainActivity::class.java
                                            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        )
                                        displayToast("Firebase authentication successful")
                                    } else {
                                        // When task is unsuccessful display Toast
                                        displayToast("Authentication Failed :" + task.exception!!.message)
                                    }
                                })
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }else{
                println("başarısız : "+signInAccountTask.exception)

            }
        }
    }

    private fun playAnimation(animalImage: LottieAnimationView, currentPosition: Int) {
        animalImage.setAnimation(animals[currentPosition])
        animalImage.playAnimation()
    }

    private fun displayToast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }


}



