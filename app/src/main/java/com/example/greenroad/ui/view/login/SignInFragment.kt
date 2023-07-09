package com.example.greenroad.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.greenroad.R
import com.example.greenroad.databinding.FragmentSignInBinding
import com.example.greenroad.ui.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private var _binding: FragmentSignInBinding? = null

    @Inject
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controlCurrentUser()
    }

    private fun controlCurrentUser() {
        val firebaseUser = loginViewModel.firebaseCurrentUser()

        if (firebaseUser != null) {

            startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } else {
            println("User is null")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding!!.toSignUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUp)
        }
        _binding!!.signInButton.setOnClickListener {
            val email = _binding!!.usernameEditText.text.toString()
            val pass = _binding!!.passwordEditText.text.toString()
            loginViewModel.signIn(email, pass)
            loginViewModel.isSuccessSignIn.observe(viewLifecycleOwner) {
                if (it) {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Fail!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}