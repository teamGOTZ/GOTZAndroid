package com.gotz.presentation.view.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gotz.base.BaseViewModel
import com.gotz.presentation.util.Event

class TutorialViewModel: BaseViewModel() {

    private var _btnClickEvent = MutableLiveData<Event<Boolean>>()
    val btnClickEvent: LiveData<Event<Boolean>> get() = _btnClickEvent

    init{

    }

    fun btnClick(){
        _btnClickEvent.value = Event(true)
    }
}