package com.gotz.presentation.view.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gotz.base.BaseViewModel
import com.gotz.domain.usecase.user.*
import com.gotz.base.util.Event
import com.gotz.base.util.GLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val createNameUseCase: CreateNameUseCase,
    private val readNameUseCase: ReadNameUseCase,
    private val updateAgeUseCase: UpdateAgeUseCase,
    private val updateGenderUseCase: UpdateGenderUseCase,
    private val readAgeUseCase: ReadAgeUseCase,
    private val readGenderUseCase: ReadGenderUseCase
): BaseViewModel() {

    private val _btnClickEvent = MutableLiveData<Event<Boolean>>()
    val btnClickEvent: LiveData<Event<Boolean>> get() = _btnClickEvent

    private val _btnSkipEvent = MutableLiveData<Event<Boolean>>()
    val btnSkipEvent: LiveData<Event<Boolean>> get() = _btnSkipEvent

    private val _btnVisibility = MutableLiveData<Boolean>()
    val btnVisibility: LiveData<Boolean> get() = _btnVisibility

    private val _termsEnable = MutableLiveData<Boolean>()
    val termsEnabled: LiveData<Boolean> get() = _termsEnable

    private val _privacyEnable = MutableLiveData<Boolean>()
    val privacyEnabled: LiveData<Boolean> get() = _privacyEnable

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> get() = _age

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> get() = _gender

    private val _nameText = MutableLiveData<String>()
    val nameText: LiveData<String> get() = _nameText

    init{
        _btnVisibility.value = false
        _termsEnable.value = false
        _privacyEnable.value = false
        _gender.value = ""
    }

    fun checkTerms(enabled: Boolean) {

    }

    fun checkPrivacy(enabled: Boolean) {

    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setAge(age: Int) {
        _age.value = age
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int){
        _btnVisibility.value = s.toString().isNotEmpty()
        _nameText.value = s.toString()
    }

    fun btnSkip() {
        _btnSkipEvent.value = Event(true)
    }

    fun btnClick(){
        _btnClickEvent.value = Event(true)
    }

    fun btnClickAndInsertName(){
        btnClick()
        insertName(nameText.value!!)
    }

    fun btnClickAndUpdateAge(){
        btnClick()
        updateAge(age.value!!)
    }

    fun btnClickAndUpdateGender() {
        btnClick()
        updateGender(gender.value!!)
    }

    fun getHelloText(): String =
        "${nameText.value}님의 일상을\n함께 만들어가요!"

    private fun insertName(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            createNameUseCase(name).catch { flowCollector ->
                GLog.messageLog(flowCollector.message.toString())
            }.collect{ isSuccess ->
                GLog.messageLog(isSuccess.toString())
            }
        }
    }

    private fun updateAge(age: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            updateAgeUseCase(age)
        }
    }

    private fun updateGender(gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateGenderUseCase(gender)
        }
    }

    fun updateData(name: String, age: Int, gender:String) {
        insertName(name)
        updateAge(age)
        updateGender(gender)
    }

    suspend fun readName(): Flow<String> =
        readNameUseCase()

    suspend fun readAge(): Flow<Int> =
        readAgeUseCase()

    suspend fun readGender(): Flow<String> =
        readGenderUseCase()
}