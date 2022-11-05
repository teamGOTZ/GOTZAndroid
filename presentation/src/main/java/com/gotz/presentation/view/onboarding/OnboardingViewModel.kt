package com.gotz.presentation.view.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gotz.base.BaseViewModel
import com.gotz.domain.usecase.user.CreateNameUseCase
import com.gotz.presentation.util.Event
import com.gotz.presentation.util.GotzLog.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val createNameUseCase: CreateNameUseCase
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
        viewModelScope.launch(Dispatchers.IO) {
            createNameUseCase(name).catch { flowCollector ->
                logE(flowCollector.message.toString())
            }.collect{ isSuccess ->
                logE(isSuccess.toString())
            }
        }
    }

    fun getNameHello(): String = "${nameText.value}님\n환영합니다!"
}