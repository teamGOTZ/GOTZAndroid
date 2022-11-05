package com.gotz.presentation.view.tutorial

import com.gotz.base.BaseFragment
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentTutorial3Binding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Tutorial3Fragment : BaseFragment<FragmentTutorial3Binding>(R.layout.fragment_tutorial_3) {

    private val viewModel: TutorialViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel
    }
}