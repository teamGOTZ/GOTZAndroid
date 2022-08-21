package com.gotz.presentation.view.onboarding

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gotz.domain.usecase.user.InsertNameUseCase
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.base.BaseViewModel
import com.gotz.presentation.util.Event

class OnboardingViewModel(
    private val insertNameUseCase: InsertNameUseCase,
    private val readSingleNameUseCase: ReadSingleNameUseCase
): BaseViewModel() {

    private var _btnClickEvent = MutableLiveData<Event<Boolean>>()
    val btnClickEvent: LiveData<Event<Boolean>> get() = _btnClickEvent

    private var _btnVisibility = MutableLiveData<Boolean>()
    val btnVisibility: LiveData<Boolean> get() = _btnVisibility

    init{
        _btnVisibility.value = false
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int){
        _btnVisibility.value = s.toString().isNotEmpty()
    }

    fun btnClick(){
        _btnClickEvent.value = Event(true)
    }
}