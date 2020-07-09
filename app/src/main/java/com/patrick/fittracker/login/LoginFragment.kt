package com.patrick.fittracker.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.patrick.fittracker.BaseActivity
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.classoption.ClassOptionViewModel
import com.patrick.fittracker.data.AddTrainingRecord
import com.patrick.fittracker.data.User
import com.patrick.fittracker.databinding.LoginFragmentBinding
import com.patrick.fittracker.ext.getVmFactory

class LoginFragment : BottomSheetDialogFragment() {


    private val viewModel by viewModels <LoginViewModel> {getVmFactory(user = User())}

    // Firebase Authentication
    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var RC_SIGN_IN = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginFragmentBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("276432098638-b8ks0jup4fb7u36s726lb1phgauf8hsm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.googleSignView.setOnClickListener { signIn() }


        return  binding.root

    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                Log.d("patrick login success", "${account}")
                Log.d("patrick login success", "${account.account}")
                Log.d("patrick login success", "${account.email}")
                Log.d("patrick login success", "${account.idToken}")
                Log.d("patrick login success", "${account.photoUrl}")

                viewModel.addUnserInfo.value?.user?.info_image = "${account.photoUrl}"
                viewModel.addUnserInfo.value?.user?.name = "${account.displayName}"
                viewModel.addUnserInfo.value?.user?.id = "${account.id}"

                Log.d("00000000000000000","${viewModel.addUnserInfo.value?.user?.id}")

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth?.currentUser
                    viewModel.addUnserInfo.value?.user?.id?.let { User(it) }?.let { viewModel.userAdd(user = it) }
                    viewModel.addUnserInfo.value?.user?.name?.let { User(it) }?.let { viewModel.userAdd(user = it) }
                    viewModel.addUnserInfo.value?.user?.info_image?.let { User(it) }?.let { viewModel.userAdd(user = it) }
                    Log.d("00000000000000000","${viewModel.addUnserInfo.value?.user?.name}")

                    viewModel.uploadUserInfo(addTrainingRecord = AddTrainingRecord())

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    view?.let { Snackbar.make(it, "Authentication Failed.", Snackbar.LENGTH_SHORT).show() }
//                        updateUI(null)
                }

            }
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth?.currentUser
    }


}
