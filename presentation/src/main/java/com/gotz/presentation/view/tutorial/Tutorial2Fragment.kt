package com.gotz.presentation.view.tutorial

import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentTutorial2Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Tutorial2Fragment : BaseFragment<FragmentTutorial2Binding>(R.layout.fragment_tutorial_2) {

    private val viewModel: TutorialViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}