package com.gotz.presentation.view.tutorial

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.gotz.presentation.base.BaseActivity
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityTutorialBinding
import com.gotz.presentation.util.EventObserver
import com.gotz.presentation.view.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialActivity: BaseActivity<ActivityTutorialBinding>(R.layout.activity_tutorial) {

    private val viewModel: TutorialViewModel by viewModel()

    private var host: Fragment? = null
    private var navController: NavController? = null

    override fun initActivity() {
        binding.viewmodel = viewModel
    }

    override fun initNavigation() {
        host = supportFragmentManager.findFragmentById(R.id.fcv_tutorial)
        navController = host?.findNavController()
    }

    override fun initObserver() {
        viewModel.btnClickEvent.observe(this, EventObserver{
            navController?.currentDestination?.label.let{ charSequence ->
                when(charSequence.toString()){
                    "Tutorial1Fragment" -> {
                        navController?.navigate(R.id.action_fragment_tutorial_1_to_fragment_tutorial_2)
                    }
                    "Tutorial2Fragment" -> {
                        navController?.navigate(R.id.action_fragment_tutorial_2_to_fragment_tutorial_3)
                    }
                    "Tutorial3Fragment" -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }
}