package com.gotz.views.main.prepare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gotz.repoository.RoomCalendarMemoRepositoryImpl
import java.lang.IllegalArgumentException

class MainPrepareHelloViewModelFactory (private val repository: RoomCalendarMemoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainPrepareHelloViewModel::class.java)){
            return MainPrepareHelloViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
