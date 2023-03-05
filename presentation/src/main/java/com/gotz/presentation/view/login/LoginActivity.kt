package com.gotz.presentation.view.login

import android.content.Intent
import com.bumptech.glide.Glide
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityLoginBinding
import com.gotz.presentation.view.permission.PermissionActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun initActivity() {
        binding.btnLoginNext.setOnClickListener {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
            finish()
        }

        Glide.with(this).load(R.raw.gif_on_boarding_4).into(binding.ivLoginCharacter)
    }
}