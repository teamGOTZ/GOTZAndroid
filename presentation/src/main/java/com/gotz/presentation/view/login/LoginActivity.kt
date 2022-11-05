package com.gotz.presentation.view.login

import android.content.Intent
import com.gotz.base.BaseActivity
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityLoginBinding
import com.gotz.presentation.view.onboarding.OnboardingActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun initActivity() {
        binding.btnLoginNext.setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }
    }
}