package com.gotz.presentation.view.my

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.gotz.presentation.R
import com.gotz.base.BaseFragment
import com.gotz.base.util.StringUtil
import com.gotz.domain.usecase.user.ReadNameUseCase
import com.gotz.presentation.BuildConfig
import com.gotz.presentation.databinding.FragmentMyBinding
import com.gotz.presentation.view.calendar.calendar.CalendarFragment
import com.gotz.presentation.view.calendar.schedule.ScheduleAddActivity
import com.gotz.presentation.view.profile.ProfileActivity
import com.gotz.presentation.view.webview.WebViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.koin.android.ext.android.inject

class MyFragment: BaseFragment<FragmentMyBinding>(R.layout.fragment_my)  {

    private val readNameUseCase: ReadNameUseCase by inject()

    override fun initFragment() {

    }

    override fun initView() {
        binding.run {
            ivMySetProfile.setOnClickListener {
                context?.let{
                    val intent = Intent(context, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }

            tvTerms.setOnClickListener {
                context?.let {
                    val intent = Intent(it, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_TERMS_AND_CONDITIONS)
                        putExtra(WebViewActivity.EXTRA_TITLE, getString(R.string.terms_of_service_kr))
                    }
                    startActivity(intent)
                }
            }

            tvPrivacy.setOnClickListener {
                context?.let {
                    val intent = Intent(it, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_PRIVACY)
                        putExtra(WebViewActivity.EXTRA_TITLE, getString(R.string.privacy_policy_kr))
                    }
                    startActivity(intent)
                }
            }

            clGotzBanner.setOnClickListener {
                context?.let {
                    val intent = Intent(it, WebViewActivity::class.java).apply {
                        putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_ON_BOARDING)
                        putExtra(WebViewActivity.EXTRA_TITLE, getString(R.string.want_to_know_gotz_kr))
                    }
                    startActivity(intent)
                }
            }

            tvScheduleMove.setOnClickListener {
                val intent = Intent(context, ScheduleAddActivity::class.java).apply {
                    putExtra(CalendarFragment.DATE_MILLIS, DateTime.now().millis)
                }
                startActivity(intent)
            }

            tvVersion.text = getString(R.string.version_info_kr, BuildConfig.GOTZ_APP_VERSION)
        }

        setName()
    }

    private fun setName() {
        lifecycleScope.launch(Dispatchers.IO) {
            val name = readNameUseCase().first()

            withContext(Dispatchers.Main) {
                binding.tvMyName.text = name
            }
        }
    }

    companion object{
        fun newInstance() = MyFragment()
    }
}