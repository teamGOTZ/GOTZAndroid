package com.gotz.presentation.view.tutorial

import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentTutorial1Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Tutorial1Fragment : BaseFragment<FragmentTutorial1Binding>(R.layout.fragment_tutorial_1) {

    private val viewModel: TutorialViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}