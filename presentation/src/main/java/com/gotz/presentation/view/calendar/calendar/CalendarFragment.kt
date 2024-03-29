package com.gotz.presentation.view.calendar.calendar

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.gotz.presentation.R
import com.gotz.presentation.base.BaseFragment
import com.gotz.presentation.extension.gone
import com.gotz.presentation.extension.invisible
import com.gotz.presentation.extension.setOnSingleClickListener
import com.gotz.presentation.extension.visible
import com.gotz.presentation.util.CalendarUtil
import com.gotz.presentation.util.CalendarUtil.startDayOfWeek
import com.gotz.presentation.util.GpsUtil
import com.gotz.presentation.util.NetworkUtil
import com.gotz.presentation.util.PermissionUtil.isPermissionGranted
import com.gotz.presentation.util.StringUtil
import com.gotz.domain.model.Schedule
import com.gotz.presentation.BuildConfig
import com.gotz.presentation.databinding.FragmentCalendarBinding
import com.gotz.presentation.util.GLog
import com.gotz.presentation.view.calendar.WeatherViewModel
import com.gotz.presentation.view.calendar.calendar.CalendarViewModel.Companion.CALENDAR_MONTH
import com.gotz.presentation.view.calendar.calendar.CalendarViewModel.Companion.CALENDAR_WEEK
import com.gotz.presentation.view.calendar.calendar.adapter.CalendarLongAdapter
import com.gotz.presentation.view.calendar.calendar.adapter.CalendarScheduleAdapter
import com.gotz.presentation.view.calendar.calendar.adapter.CalendarScheduleItemDecoration
import com.gotz.presentation.view.calendar.calendar.adapter.CalendarShortAdapter
import com.gotz.presentation.view.calendar.schedule.ScheduleAddActivity
import com.gotz.presentation.view.calendar.schedule.ScheduleShowActivity
import com.gotz.presentation.view.calendar.schedule.ScheduleShowActivity.Companion.EXTRA_SCHEDULE
import com.gotz.presentation.view.calendar.schedule.ScheduleViewModel
import com.gotz.presentation.view.webview.WebViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CalendarFragment : BaseFragment<FragmentCalendarBinding>(R.layout.fragment_calendar) {

    private val calendarViewModel: CalendarViewModel by sharedViewModel()
    private val scheduleViewModel: ScheduleViewModel by viewModel()
    private val weatherViewModel: WeatherViewModel by viewModel()

    private lateinit var calendarShortAdapter: CalendarShortAdapter
    private lateinit var calendarLongAdapter: CalendarLongAdapter
    private lateinit var calendarScheduleAdapter: CalendarScheduleAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val vpCalendarLongCallback = object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            GLog.messageLog("vpLong:onPageSelected")
            binding.mlCalendar.run {
                if(currentState == endState) {
                    GLog.messageLog("vpLong:onPageSelected ${position - START_POSITION}")
                    GLog.messageLog("vpLong:onPageSelected ${CalendarUtil.getDateTimeForMonthlyCalendar(DateTime.now(), position - START_POSITION).toString("yyyy-MM-dd")}")
                    val date = CalendarUtil.getDateTimeForMonthlyCalendar(DateTime.now(), position - START_POSITION).millis
                    calendarViewModel.setDateTime(date)
                }
            }
        }
    }

    private val vpCalendarShortCallback = object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            GLog.messageLog("vpShort:onPageSelected")
            binding.mlCalendar.run {
                if(currentState == startState) {
                    GLog.messageLog("vpShort:onPageSelected ${position - START_POSITION}")
                    GLog.messageLog("vpShort:onPageSelected ${CalendarUtil.getDateTimeForWeeklyCalendar(DateTime.now(), position - START_POSITION).toString("yyyy-MM-dd")}")
                    val date = CalendarUtil.getDateTimeForWeeklyCalendar(DateTime.now(), position - START_POSITION).millis
                    calendarViewModel.setDateTime(date)
                }
            }
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            GLog.messageLog("vpShort:onPageScrolled")
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            GLog.messageLog("vpShort:onPageScrollStateChanged")
        }
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2

        const val DATE_MILLIS = "DATE_MILLIS"

        fun newInstance() = CalendarFragment()
    }

    override fun initFragment() {
        calendarShortAdapter = CalendarShortAdapter(requireActivity())
        calendarLongAdapter = CalendarLongAdapter(requireActivity())
        calendarScheduleAdapter = CalendarScheduleAdapter()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    val point = GpsUtil.convertToCoordinates(location.latitude, location.longitude)
                    GLog.messageLog("${location.longitude} /// ${location.latitude}")
                    GLog.messageLog("${point.x} /// ${point.y}")

                    if(NetworkUtil.isNetworkEnable(context)) {
                        val address = Geocoder(context).getFromLocation(location.latitude, location.longitude, 1)[0]?:Geocoder(context).getFromLocation(37.3957122, 127.1105181, 1)[0]
                        binding.tvLocation.text = StringUtil.getAddressString(address)

                        lifecycleScope.launch {
                            val weather = weatherViewModel.readWeather(
                                StringUtil.getBaseDate(DateTime.now()),
                                StringUtil.getBaseTime(DateTime.now()),
                                point.x,
                                point.y).first()

                            GLog.messageLog("$weather")

                            setWeatherData(weather.temperature, weather.skyStatus)

                        }
                    }
                }
            }
        }

        createLocationRequest()
        startLocationUpdates()

        lifecycleScope.launch(Dispatchers.IO) {
            val scheduleWithDate = scheduleViewModel.readDailyScheduleUseCase(DateTime.now()).first()

            withContext(Dispatchers.Main) {
                binding.run {
                    if(scheduleWithDate == null) {
                        calendarScheduleAdapter.clearItems()
                        rvCalendar.gone()
                        clCalendarList.visible()
                    }
                    else {
                        calendarScheduleAdapter.initItems(scheduleWithDate.schedule)
                        rvCalendar.visible()
                        clCalendarList.gone()
                    }
                }
            }

        }
    }

    private fun initWeatherData() {
        binding.run {
            tvTemperature.text = "=="
            tvLocation.text = "=="
            tvWeatherUpdateAt.invisible()
            Glide.with(this@CalendarFragment).load(R.drawable.ic_weather_none).into(ivWeather)
        }
    }

    private fun setWeatherData(temperature: Float, skyStatus: Int) {
        binding.run {
            if(skyStatus == -1) {
                tvTemperature.text = "=="
                tvLocation.text = "=="
                tvWeatherUpdateAt.invisible()
            }
            else {
                tvTemperature.text = "$temperature"
                tvWeatherUpdateAt.text = getUpdatedAt(DateTime.now())
                tvWeather.text = getWeatherStr(skyStatus)
                tvWeatherUpdateAt.visible()

                getWeatherIcon(skyStatus)?.let { resId ->
                    Glide.with(this@CalendarFragment).load(resId).into(ivWeather)
                }
            }
        }
    }

    private fun getWeatherStr(skyStatus: Int): String =
        when(skyStatus) {
            0 -> getString(R.string.weather_is_sun_kr)
            1 -> getString(R.string.weather_is_rain_kr)
            2 -> getString(R.string.weather_is_rain_snow_kr)
            3 -> getString(R.string.weather_is_snow_kr)
            else -> getString(R.string.weather_is_none_kr)
        }

    private fun getWeatherIcon(skyStatus: Int): Int? =
        when(skyStatus) {
            0 -> R.drawable.ic_weather_sun
            1 -> R.drawable.ic_weather_rain
            2 -> R.drawable.ic_weather_rain_snow
            3 -> R.drawable.ic_weather_snow
            else -> null
        }

    private fun getUpdatedAt(dateTime: DateTime): String =
        getString(R.string.updated_kr, StringUtil.getUpdateAt(dateTime))

    override fun initView() {
        binding.run {
            calendarVM = calendarViewModel

            tvToday.text = DateTime.now().toString("MM/dd")

            btnMotion.setOnSingleClickListener { btn ->
                if (mlCalendar.currentState == mlCalendar.startState) {
                    mlCalendar.transitionToEnd()
                    calendarViewModel.setCalendarStatus(CALENDAR_WEEK)

                    btn.setBackgroundResource(R.drawable.img_calendar_btn_down)
                } else {
                    mlCalendar.transitionToStart()
                    calendarViewModel.setCalendarStatus(CALENDAR_MONTH)

                    btn.setBackgroundResource(R.drawable.img_calendar_btn_up)
                }
            }

            btnCalendarToday.setOnClickListener {
                val now = DateTime.now()
                binding.run {
                    vpCalendarShort.currentItem = START_POSITION + getShortPosition(now)
                    vpCalendarLong.currentItem = START_POSITION + getLongPosition(now)
                }

                calendarViewModel.setDateTime(now.millis)
            }

            vpCalendarShort.run {
                adapter = calendarShortAdapter
                currentItem = START_POSITION
            }

            vpCalendarLong.run {
                adapter = calendarLongAdapter
                currentItem = START_POSITION
            }

            fabCalendar.setOnClickListener {
                val intent = Intent(context, ScheduleAddActivity::class.java).apply {
                    putExtra(DATE_MILLIS, calendarViewModel.dateTime.value?.millis)
                }
                startActivity(intent)
            }

            rvCalendar.run {
                adapter = calendarScheduleAdapter.apply {
                    setOnLayoutClickListener(object: CalendarScheduleAdapter.OnLayoutClickListener{
                        override fun onLayoutClick(view: View, item: Schedule) {
                            val intent = Intent(context, ScheduleShowActivity::class.java).apply {
                                putExtra(EXTRA_SCHEDULE, item)
                            }
                            startActivity(intent)
                        }
                    })
                }

                addItemDecoration(CalendarScheduleItemDecoration(context))
            }

            clGotzBanner.setOnClickListener {
                val intent = Intent(context, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.EXTRA_LOAD_URL, BuildConfig.GOTZ_URL_ON_BOARDING)
                    putExtra(WebViewActivity.EXTRA_TITLE, "GOT'Z를 알고싶다면?")
                }
                startActivity(intent)
            }

            tvCalendarAddSchedule.setOnClickListener {
                val intent = Intent(context, ScheduleAddActivity::class.java).apply {
                    putExtra(DATE_MILLIS, calendarViewModel.dateTime.value?.millis)
                }
                startActivity(intent)
            }
        }

        initWeatherData()
    }

    override fun initViewModel() {
        calendarViewModel.run {
            dateTime.observe(this@CalendarFragment) { dateTime ->
                GLog.messageLog("dateTime:observe ${dateTime.toString("yyyy-MM-dd")}")
                binding.run {
                    vpCalendarLong.currentItem = START_POSITION + getLongPosition(dateTime)
                    vpCalendarShort.currentItem = START_POSITION + getShortPosition(dateTime)
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    observeDailySchedule(dateTime, clickStatus.value)
                }
            }

            lifecycleScope.launch(Dispatchers.IO) {
                dateTime.value?.let{ dateTime ->
                    observeDailySchedule(dateTime, clickStatus.value)
                }
            }
        }

        registerCallback()
    }

    suspend fun observeDailySchedule(dateTime: DateTime, clickStatus: Boolean?) {
        scheduleViewModel.readDailyScheduleUseCase(dateTime).collect{ scheduleWithDate ->
            withContext(Dispatchers.Main) {
                if(clickStatus == true) {
                    binding.run {
                        if(scheduleWithDate == null) {
                            calendarScheduleAdapter.clearItems()
                            rvCalendar.gone()
                            clCalendarList.visible()
                        }
                        else {
                            calendarScheduleAdapter.initItems(scheduleWithDate.schedule)
                            rvCalendar.visible()
                            clCalendarList.gone()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            val dateTime = calendarViewModel.getDateTime()
            val scheduleWithDate = scheduleViewModel.readDailyScheduleUseCase(dateTime).first()
            withContext(Dispatchers.Main) {
                binding.run {
                    if(scheduleWithDate == null) calendarScheduleAdapter.clearItems()
                    else calendarScheduleAdapter.initItems(scheduleWithDate.schedule)
                }
            }
        }
    }

    override fun onDestroy() {
        unregisterCallback()
        super.onDestroy()
    }

    private fun registerCallback() {
        binding.run {
            vpCalendarShort.registerOnPageChangeCallback(vpCalendarShortCallback)
            vpCalendarLong.registerOnPageChangeCallback(vpCalendarLongCallback)
        }
    }

    private fun unregisterCallback() {
        binding.run {
            vpCalendarShort.unregisterOnPageChangeCallback(vpCalendarShortCallback)
            vpCalendarLong.unregisterOnPageChangeCallback(vpCalendarLongCallback)
        }
    }

    private fun getLongPosition(dateTime: DateTime): Int {
        val year1: Int = dateTime.year
        val year2: Int = DateTime.now().year
        val month1: Int = dateTime.monthOfYear
        val month2: Int = DateTime.now().monthOfYear

        return (year1 - year2) * 12 + (month1 - month2)
    }

    private fun getShortPosition(dateTime: DateTime): Int {
        val dateTime1: DateTime = startDayOfWeek(dateTime)
        val dateTime2: DateTime = startDayOfWeek(
            DateTime(
                DateTime.now().year,
                DateTime.now().monthOfYear,
                DateTime.now().dayOfMonth,
                0,
                0,
                0
            )
        )

        return ((dateTime1.millis - dateTime2.millis) / 604800000L).toInt()
    }



    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.HOURS.toMillis(1)).build()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        context?.let{
            if (it.isPermissionGranted(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }
    }
}