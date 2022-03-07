package com.example.wgpgkt.views.main.join.general

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wgpgkt.base.BaseViewModel
import com.example.wgpgkt.model.RegisterModel
import com.example.wgpgkt.repoository.ApiRepositoryImpl
import com.example.wgpgkt.util.NetworkUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception



class MainJoinGeneralViewModel: BaseViewModel() {
    val str_id = MutableLiveData<String>()
    val str_pw = MutableLiveData<String>()
    val str_pw2 = MutableLiveData<String>()
    val str_name = MutableLiveData<String>()
    val str_birth = MutableLiveData<String>()
    val str_phone = MutableLiveData<String>()

    fun btnJoinClick(){
        //test()

        Log.e("ViewModel : ", "start")
        viewModelScope.launch {
            try{
                val response = async { ApiRepositoryImpl.postRegister(str_birth.value.toString()
                    , str_id.value.toString()
                    , str_pw.value.toString()
                    , str_phone.value.toString()
                    , str_name.value.toString())}.await()
                Log.e("ViewModel : ", "success")

                if(response.code() == 200){
                    Log.e("MainGeneralViewModel : ", response.code().toString())
                    Log.e("MainGenrealViewModel : ", response.body().toString())
                }
                else if(response.code() == 400){
                    Log.e("MainGeneralViewModel : ", response.code().toString())
                    Log.e("MainGeneralViewModel : ", NetworkUtil.getErrorResponse(response.errorBody()!!).toString())
                }
            }catch (e:Exception){
                Log.e("ViewModel : ", e.message.toString())
            }
        }
    }
/*
    fun test(){
        Log.e("TEST :: ", "start")
        try{
            var data: Call<RegisterModel>
            data = ApiRepositoryImpl.testAPI("2022-01-01","abcdefg@abc.com","abcd1234!","123-4567-8911","abc")
            data.enqueue(object: Callback<RegisterModel>{
                override fun onResponse(
                    call: Call<RegisterModel>,
                    response: Response<RegisterModel>,
                ) {
                    var result = response.body()
                    Log.e("Response :: ", "success")
                    Log.e("Response :: code :: ", response.code().toString())
                    Log.e("Response :: message :: ", response.message().toString())
                    Log.e("Response :: ",
                        NetworkUtil.getErrorResponse(response.errorBody()!!).toString())
                }

                override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                    Log.e("Throwable :: ", t.message.toString())
                }

            })
        }catch (e: Exception){
            Log.e("Exception :: ", e.message.toString())
        }
    }
 */
}