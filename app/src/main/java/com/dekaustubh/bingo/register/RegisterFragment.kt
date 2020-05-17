package com.dekaustubh.bingo.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PlayGamesAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

private const val RC_SIGN_IN = 9001

class RegisterFragment : DaggerFragment(), RegisterContract.View {

    @Inject
    lateinit var presenter: RegisterContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    @OnClick(R.id.sign_in_button)
    fun onRegisterClicked() {
        startSignInIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()

        return inflater.inflate(R.layout.fragment_register, container, false).also {
            ButterKnife.bind(this, it)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    override fun showUser(user: User) {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    override fun showProgressBar() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun startSignInIntent() {
        startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("OnActivity result!!! $data")
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.result
                Timber.d("firebaseAuthWithGoogle: ${account?.id}")
                val user = FirebaseAuth.getInstance().currentUser
                Timber.d("User ${user?.uid}")
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, get user from the account.
                            firebaseAuthWithPlayGames(account!!)
                        } else {
                            Timber.e("Task execution failed...")
                        }
                    }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.e(e, "Google sign in failed")
            }
        }
    }

    private fun firebaseAuthWithPlayGames(acct: GoogleSignInAccount) {
        val auth = Firebase.auth
        val credential = PlayGamesAuthProvider.getCredential(acct.idToken!!)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User received successfully!
                    auth.currentUser?.let {
                        presenter.saveUserPreference(it)
                    }
                } else {
                    toaster.showToast("Auth failed")
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
