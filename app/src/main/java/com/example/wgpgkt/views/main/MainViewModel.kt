package com.example.wgpgkt.views.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wgpgkt.base.BaseViewModel
import com.example.wgpgkt.repoository.ApiRepositoryImpl
import com.example.wgpgkt.util.NetworkUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    val str_id = MutableLiveData<String>()
    val str_pw = MutableLiveData<String>()

    init {

    }

/*
    fun clickLogin(){
        Log.e("MainViewModel :: ", str_id.value.toString())
        Log.e("MainViewModel :: ", str_pw.value.toString())

        viewModelScope.launch{
            try{
                val response = async { ApiRepositoryImpl.postLogin(str_id.value.toString()
                    ,str_pw.value.toString())}.await()
                if(response.code() == 200){
                    Log.e("MainGeneralViewModel : ", response.code().toString())
                    Log.e("MainGeneralViewModel : ", response.headers().toString())
                    Log.e("MainGeneralViewModel : ", response.headers().get("Authorization").toString())
                    Log.e("MainGenrealViewModel : ", response.body().toString())
                }
                else if(response.code() == 400){
                    val errorResponse = NetworkUtil.getErrorResponse(response.errorBody()!!)
                    Log.e("MainGeneralViewModel : ", response.code().toString())
                    Log.e("MainGeneralViewModel : ", response.message().toString())
                    Log.e("MainGeneralViewModel : ", errorResponse.message.toString())
                }
            }catch (e:Exception){
                Log.e("MainViewModel :: ", e.message.toString())
            }
        }
    }

    fun clickJoin(){

    }

    fun clickFind(){

    }*/
}