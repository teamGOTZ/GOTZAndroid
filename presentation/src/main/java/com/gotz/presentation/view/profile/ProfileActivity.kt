package com.gotz.presentation.view.profile

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.gotz.base.BaseActivity
import com.gotz.base.custom.CheckLayout
import com.gotz.base.extension.clickable
import com.gotz.base.extension.clickableNot
import com.gotz.presentation.R
import com.gotz.presentation.databinding.ActivityProfileBinding
import com.gotz.presentation.view.onboarding.OnboardingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity: BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val onboardingViewModel: OnboardingViewModel by viewModel()

    private val genderList: List<String> by lazy { listOf(getString(R.string.gender_man_kr), getString(R.string.gender_woman_kr), getString(R.string.gender_etc_kr)) }

    private var nickname = ""
    private var age = 0
    private var gender = ""

    override fun initActivity() {
        binding.viewmodel = onboardingViewModel
    }

    override fun initView() {
        binding.run {
            lifecycleScope.launch(Dispatchers.IO) {
                nickname = onboardingViewModel.readName().first()
                age = onboardingViewModel.readAge().first()
                gender = onboardingViewModel.readGender().first()

                withContext(Dispatchers.Main) {
                    etNickname.editText.setText(nickname)
                    spnAge.setSelection(age)
                    setGender()
                }
            }


            spnAge.apply {
                val adapter = object : ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item){
                    override fun isEnabled(position: Int): Boolean {
                        return position != 0
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = super.getDropDownView(position, convertView, parent)
                        val textView = view as TextView
                        if(position == 0) textView.setTextColor(resources.getColor(R.color.Gray_200, null))
                        else textView.setTextColor(resources.getColor(R.color.Gray_900, null))
                        return view
                    }


                }.apply{
                    addAll(spinnerItemList())
                }
                this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        age = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
                this.adapter = adapter

            }

            etNickname.text.observe(this@ProfileActivity) { text ->
                if (text.isNotEmpty()){
                    tvSave.clickable()
                    tvSave.setTextColor(resources.getColor(R.color.Primary, null))
                }
                else{
                    tvSave.clickableNot()
                    tvSave.setTextColor(resources.getColor(R.color.Gray_400, null))
                }
            }

            tvSave.setOnClickListener {
                onboardingViewModel.updateData(nickname, age, gender)
                finish()
            }

            btnBack.setOnClickListener {
                finish()
            }

            initCheckLayout()
        }
    }

    private fun spinnerItemList(): List<String> {
        val result = mutableListOf<String>("나이 선택")
        for(num in 1 .. 100) {
            result.add(num.toString())
        }
        return result
    }

    private fun setGender() {
        val checkLayout = listOf(binding.checkLayout1, binding.checkLayout2, binding.checkLayout3)
        when(gender) {
            getString(R.string.gender_man_kr) -> {
                checkLayout.forEachIndexed { index, it ->
                    if(index == 0) it.setLayoutStatus(CheckLayout.STATUS_ENABLE)
                    else it.setLayoutStatus(CheckLayout.STATUS_DISABLE)
                }
            }
            getString(R.string.gender_woman_kr) -> {
                checkLayout.forEachIndexed { index, it ->
                    if(index == 1) it.setLayoutStatus(CheckLayout.STATUS_ENABLE)
                    else it.setLayoutStatus(CheckLayout.STATUS_DISABLE)
                }
            }
            getString(R.string.gender_etc_kr) -> {
                checkLayout.forEachIndexed { index, it ->
                    if(index == 2) it.setLayoutStatus(CheckLayout.STATUS_ENABLE)
                    else it.setLayoutStatus(CheckLayout.STATUS_DISABLE)
                }
            }
            else -> {

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
                gender = genderList[index]
                checkLayout.filter { layout ->
                    it != layout
                }.forEach {
                    it.setLayoutStatus(CheckLayout.STATUS_DISABLE)
                }
            }
        }
    }
}