package com.gotz.views.main.prepare

import androidx.lifecycle.MutableLiveData
import com.gotz.base_legacy.BaseViewModelLegacy

class MainPrepareNameViewModel: BaseViewModelLegacy() {
    val name = MutableLiveData<String>()
}