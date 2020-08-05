package com.patrick.fittracker.login2

import android.content.ContentValues
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.patrick.fittracker.NavigationDirections

import com.patrick.fittracker.R
import com.patrick.fittracker.UserManger
import com.patrick.fittracker.data.User
import com.patrick.fittracker.data.UserProfile
import com.patrick.fittracker.databinding.LoginFragmentBinding
import com.patrick.fittracker.databinding.LoginTwoFragmentBinding
import com.patrick.fittracker.ext.getVmFactory
import com.patrick.fittracker.login.LoginViewModel
import com.patrick.fittracker.network.LoadApiStatus
import com.patrick.fittracker.util.Util.getColor
import kotlinx.android.synthetic.main.activity_main.*


class LoginTwoFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel> { getVmFactory(user = User()) }

    // Firebase Authentication
    private var auth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var RC_SIGN_IN = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginTwoFragmentBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("276432098638-b8ks0jup4fb7u36s726lb1phgauf8hsm.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.signinTitle.setOnClickListener { signIn() }

        val mainTitle = binding.loginMainTitle
        val span: Spannable = SpannableString(mainTitle.text)
        span.setSpan(
            ForegroundColorSpan(getColor(R.color.colorAccent)),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mainTitle.text = span

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            viewModel.showLoadingStatus()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                viewModel.insertUserValue(account)

                UserManger.userID = account.id
                UserManger.userName = account.displayName
                UserManger.userImage = "${account.photoUrl}"
                UserManger.userEmail = "${account.email}"

                UserManger.userData.name = "${account.displayName}"
                UserManger.userData.email = "${account.email}"
                UserManger.userData.id = "${account.id}"
                UserManger.userData.userProfile?.info_image = "${account.photoUrl}"

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
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
                    user?.let {
                        viewModel.syncUserInfo(user)
                    }

                    findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    view?.let {
                        Snackbar.make(it, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    }
                }

            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth?.currentUser
        if (UserManger.isLogin()) {
            findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
        }
    }

    override fun onResume() {
        (activity as AppCompatActivity).bottomNavVIew?.visibility = View.GONE
        (activity as AppCompatActivity).toolbar.visibility = View.GONE
        (activity as AppCompatActivity).main_title_spannable_test.visibility = View.GONE

        super.onResume()
    }

    override fun onStop() {
        (activity as AppCompatActivity).bottomNavVIew?.visibility = View.VISIBLE
        (activity as AppCompatActivity).toolbar.visibility = View.VISIBLE
        (activity as AppCompatActivity).main_title_spannable_test.visibility = View.VISIBLE

        super.onStop()
    }
}
