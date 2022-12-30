package com.gotz.presentation.view.onboarding

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gotz.base.BaseFragment
import com.gotz.presentation.R
import com.gotz.presentation.databinding.FragmentOnboardingAgeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OnboardingAgeFragment: BaseFragment<FragmentOnboardingAgeBinding>(R.layout.fragment_onboarding_age) {
    private val viewModel: OnboardingViewModel by sharedViewModel()

    override fun initFragment() {
        binding.viewmodel = viewModel

        Glide.with(this).load(R.raw.gif_on_boarding_5).into(binding.ivContents)
        lifecycleScope.launch(Dispatchers.IO) {
            val name = viewModel.readName().first()

            withContext(Dispatchers.Main) {
                binding.tvContents.text = getString(R.string.on_boarding_age_text, name)
            }
        }

    }

    override fun initView() {
        binding.run {
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
                        if(position == 0) textView.setTextColor(resources.getColor(R.color.Gray_200))
                        else textView.setTextColor(resources.getColor(R.color.Gray_900))
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
                        viewModel.setAge(position)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
                this.adapter = adapter
            }

            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun spinnerItemList(): List<String> {
        val result = mutableListOf<String>("나이 선택")
        for(num in 1 .. 100) {
            result.add(num.toString())
        }
        return result
    }
}