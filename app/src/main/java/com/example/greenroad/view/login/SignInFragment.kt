package com.example.greenroad.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.greenroad.R
import com.example.greenroad.databinding.FragmentSignInBinding
import com.example.greenroad.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

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

            if (email.isNotBlank() && pass.isNotBlank()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
                        var intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)


                    } else {
                        Toast.makeText(context, "Fail!", Toast.LENGTH_LONG).show()

                    }

                }
            } else {
                Toast.makeText(context, "Empty login", Toast.LENGTH_LONG).show()

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}