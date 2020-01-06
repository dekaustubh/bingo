package com.dekaustubh.bingo.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.models.User
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment(), RegisterContract.View {

    @BindView(R.id.email)
    lateinit var emailEditText: EditText

    @BindView(R.id.password)
    lateinit var passwordEditText: EditText

    @BindView(R.id.overlay)
    lateinit var overlay: View

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @Inject
    lateinit var presenter: RegisterContract.Presenter

    @Inject
    lateinit var toaster: Toaster

    private var listener: OnLoginFragmentListener? = null

    @OnClick(R.id.login)
    fun onRegisterClicked() {
        progressBar.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE

        presenter.loginUser(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
    }

    @OnClick(R.id.dont_have_account)
    fun onLoginClicked() {
        listener?.clickedRegister()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false).also {
            ButterKnife.bind(this, it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLoginFragmentListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnRegisterFragmentListener")
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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun showUser(user: User) {
        progressBar.visibility = View.GONE
        overlay.visibility = View.GONE

        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    override fun showError(message: String) {
        progressBar.visibility = View.GONE
        overlay.visibility = View.GONE

        toaster.showToast(message)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    interface OnLoginFragmentListener {
        fun clickedRegister()
    }
}