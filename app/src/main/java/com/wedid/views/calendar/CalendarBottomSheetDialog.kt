package com.wedid.views.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wedid.base.BaseApplication
import com.wedid.databinding.DialogCalendarBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.joda.time.DateTime

class CalendarBottomSheetDialog(): BottomSheetDialogFragment() {
    private var _binding : DialogCalendarBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val MIN_YEAR: Int = 1970
    private val MAX_YEAR: Int = 2099

    private var year: Int? = null
    private var month: Int? = null
    /*
    private val viewModel: CalendarViewModel by activityViewModels{
        CalendarViewModelFactory((requireActivity().application as BaseApplication).roomCalendarMemoRepositoryImpl)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogCalendarBottomSheetBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.npYear.minValue = MIN_YEAR
        binding.npYear.maxValue = MAX_YEAR
        binding.npYear.value = viewModel.year.value!!
        binding.npYear.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            year = newVal
        }
        year = viewModel.year.value!!
        binding.npMonth.wrapSelectorWheel = false

        binding.npMonth.minValue = 1
        binding.npMonth.maxValue = 12
        binding.npMonth.value = viewModel.month.value!!
        binding.npMonth.setOnValueChangedListener { numberPicker, oldVal, newVal ->
            month = newVal
        }
        month = viewModel.month.value!!
        binding.npYear.wrapSelectorWheel = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(year != viewModel.year.value || month != viewModel.month.value ){

            viewModel.flagForShort.value = false
            val calendarFragment = requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0) as CalendarFragment
            val dateTime = DateTime(year!!, month!!, 1, 0, 0, 0)

            calendarFragment.setCalendar(dateTime)

            Log.e("DIALOG",requireActivity().supportFragmentManager.fragments.get(0).childFragmentManager.fragments.get(0).toString())
        }

    }

*/

}