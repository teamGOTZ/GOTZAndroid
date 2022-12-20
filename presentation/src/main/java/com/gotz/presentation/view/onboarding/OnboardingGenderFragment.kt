package com.gotz.presentation.view.onboarding

import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gotz.base.BaseFragment
import com.gotz.base.custom.CheckLayout
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentOnboardingGenderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingGenderFragment: BaseFragment<FragmentOnboardingGenderBinding>(R.layout.fragment_onboarding_gender) {
    private val viewModel: OnboardingViewModel by sharedViewModel()
    private val genderList = listOf("남자", "여자", "그 외")

    override fun initFragment() {
        binding.viewmodel = viewModel

        Glide.with(this).load(R.raw.gif_on_boarding_5).into(binding.ivContents)
        lifecycleScope.launch(Dispatchers.IO) {
            val name = viewModel.readName().first()

            withContext(Dispatchers.Main) {
                binding.tvContents.text = "${name}님의\n성별을 선택해주세요"
            }
        }
    }

    override fun initView() {
        binding.run {
            initCheckLayout()

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun initCheckLayout() {
        val checkLayout = listOf(binding.checkLayout1, binding.checkLayout2, binding.checkLayout3)

        checkLayout.forEachIndexed { index, view ->
            view.setText(genderList[index])
            view.setLayoutStatus(CheckLayout.STATUS_DISABLE)
            view.setOnClickListener { it -> it as CheckLayout
                it.setLayoutStatus(CheckLayout.STATUS_ENABLE)
                viewModel.setGender(genderList[index])
                checkLayout.filter { layout ->
                    it != layout
                }.forEach {
                    it.setLayoutStatus(CheckLayout.STATUS_DISABLE)
                }
            }
        }
    }
}