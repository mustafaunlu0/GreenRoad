package com.example.greenroad.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.greenroad.databinding.FragmentSignUpBinding
import com.example.greenroad.util.Constants.animals
import com.example.greenroad.ui.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val loginViewModel : LoginViewModel by viewModels()

    private var _binding: FragmentSignUpBinding? = null
    private var currentPosition = 0

    private lateinit var googleSignInClient: GoogleSignInClient;

    @Inject
    lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGoogle()

    }

    private fun initGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("716579750984-58fsd5o09rphmuk0tmnos2j0dns47mr3.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
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

        _binding!!.nextArrow.setOnClickListener {
           nextItem()
        }
        _binding!!.backArrow.setOnClickListener {

           prevItem()
        }

        _binding!!.nextStepButton.setOnClickListener {

            val username = _binding!!.usernameEditText.text.toString()
            val email = _binding!!.emailEditText.text.toString()
            val pass = _binding!!.passwordEditText.text.toString()
            val passO = _binding!!.passwordOtherEditText.text.toString()

            loginViewModel.signUp(email,username,pass,passO)
            loginViewModel.isSuccessSignUp.observe(viewLifecycleOwner){

                if (it){
                    displayToast("Başarılı Girişşş")
                    startActivity(
                        Intent(
                            requireActivity(),
                            MainActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                }else{
                    displayToast("HATAAA")
                }
            }

        }


        _binding!!.gmailButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, 100)
        }


    }

    fun nextItem(){
        if (currentPosition < animals.size - 1) {
            currentPosition++
            playAnimation(_binding!!.animalImage, currentPosition)
        } else {
            currentPosition = 0
            playAnimation(_binding!!.animalImage, currentPosition)
        }
    }

    fun prevItem(){
        if (currentPosition == 0) {
            currentPosition = animals.size - 1
            playAnimation(_binding!!.animalImage, currentPosition)

        } else {
            currentPosition--
            playAnimation(_binding!!.animalImage, currentPosition)
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

            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (signInAccountTask.isSuccessful) {

                try {
                    val googleSignInAccount = signInAccountTask.getResult(
                        ApiException::class.java
                    )
                    if (googleSignInAccount != null) {
                        val authCredential =
                            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

                        auth.signInWithCredential(authCredential)
                            .addOnCompleteListener(requireActivity()
                            ) { task ->
                                if (task.isSuccessful) {
                                    startActivity(
                                        Intent(
                                            requireActivity(),
                                            MainActivity::class.java
                                        ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    )
                                    displayToast("Firebase authentication successful")
                                } else {
                                    displayToast("Authentication Failed :" + task.exception!!.message)
                                }
                            }
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



