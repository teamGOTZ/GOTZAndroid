package com.gotz.presentation.view.onboarding

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gotz.domain.usecase.user.CreateNameUseCase
import com.gotz.domain.usecase.user.ReadSingleNameUseCase
import com.gotz.presentation.base.BaseViewModel
import com.gotz.presentation.util.Event
import com.gotz.presentation.util.GotzTest
import com.gotz.presentation.util.GotzTest.logE
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class OnboardingViewModel(
    private val insertNameUseCase: CreateNameUseCase
): BaseViewModel() {

    private var _btnClickEvent = MutableLiveData<Event<Boolean>>()
    val btnClickEvent: LiveData<Event<Boolean>> get() = _btnClickEvent

    private var _btnVisibility = MutableLiveData<Boolean>()
    val btnVisibility: LiveData<Boolean> get() = _btnVisibility

    private var _nameText = MutableLiveData<String>()
    val nameText: LiveData<String> get() = _nameText

    init{
        _btnVisibility.value = false
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int){
        _btnVisibility.value = s.toString().isNotEmpty()
        _nameText.value = s.toString()
    }

    fun btnClick(){
        _btnClickEvent.value = Event(true)
    }

    fun btnClickAndInsertName(){
        btnClick()
        insertName(nameText.value!!)
    }

    private fun insertName(name: String){
        Log.e("OnboardingViewModel", name)
        insertNameUseCase(name)
    }

    fun getNameHello(): String = "${nameText.value}님\n환영합니다!"
}