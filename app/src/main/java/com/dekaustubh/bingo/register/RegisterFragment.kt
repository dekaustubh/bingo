package com.dekaustubh.bingo.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.models.User
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.games.Games
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

private const val RC_SIGN_IN = 1000

class RegisterFragment : DaggerFragment(), RegisterContract.View {

    @Inject
    lateinit var presenter: RegisterContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    @OnClick(R.id.sign_in_button)
    fun onRegisterClicked() {
        startSignInIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun showError(message: String) {
        toaster.showToast(message)
    }

    private fun startSignInIntent() {
        val signInClient = GoogleSignIn.getClient(
            requireActivity(),
            GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN
        )
        val intent = signInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result?.isSuccess == true) {
                // The signed in account is stored in the result.
                val signedInAccount = result.signInAccount
                signedInAccount?.let {
                    presenter.signIn(Games.getPlayersClient(requireActivity(), signedInAccount))
                } ?: Timber.e("Error while retrieving google account!")
            } else {
                val message = result?.status?.statusMessage ?: "Error while signing in"
                AlertDialog
                    .Builder(requireActivity())
                    .setMessage(message)
                    .setNeutralButton(android.R.string.ok, null)
                    .show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
