package com.example.wgpgkt.views.main.join.general

import androidx.lifecycle.MutableLiveData
import com.example.wgpgkt.base.BaseViewModel

class MainJoinGeneralViewModel: BaseViewModel() {
    val str_id = MutableLiveData<String>()
    val str_pw = MutableLiveData<String>()
    val str_pw2 = MutableLiveData<String>()
    val str_name = MutableLiveData<String>()
    val str_birth = MutableLiveData<String>()
    val str_phone = MutableLiveData<String>()
}