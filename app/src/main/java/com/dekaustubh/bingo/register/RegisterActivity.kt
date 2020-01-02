package com.dekaustubh.bingo.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.Toaster
import com.dekaustubh.bingo.main.MainActivity
import com.dekaustubh.bingo.models.User
import dagger.android.DaggerActivity
import javax.inject.Inject

class RegisterActivity : DaggerActivity(), RegisterContract.RegisterView {

    @BindView(R.id.name)
    lateinit var nameEditText: EditText

    @BindView(R.id.email)
    lateinit var emailEditText: EditText

    @BindView(R.id.password)
    lateinit var passwordEditText: EditText

    @BindView(R.id.overlay)
    lateinit var overlay: View

    @BindView(R.id.progress_bar)
    lateinit var progressBar: ProgressBar

    @Inject
    lateinit var presenter: RegisterContract.RegisterPresenter

    @Inject
    lateinit var toaster: Toaster

    @OnClick(R.id.register)
    fun onRegisterClicked() {
        progressBar.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE

        presenter.registerUser(
            nameEditText.text.toString(),
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ButterKnife.bind(this)
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
        progressBar.visibility = View.GONE
        overlay.visibility = View.GONE

        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showError(message: String) {
        progressBar.visibility = View.GONE
        overlay.visibility = View.GONE

        toaster.showToast(message)
    }
}
