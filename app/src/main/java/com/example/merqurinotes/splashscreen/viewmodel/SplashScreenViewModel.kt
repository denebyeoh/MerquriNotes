package com.example.merqurinotes.splashscreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merqurinotes.base.BaseViewModel
import com.example.merqurinotes.splashscreen.repository.SplashScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SplashScreenViewModel @Inject constructor(repository: SplashScreenRepository) : BaseViewModel<SplashScreenRepository>(repository){

    private val retrieveTestResponseInternal = MutableLiveData<Boolean>();
    val retrieveTestResponse : MutableLiveData<Boolean>
        get() = retrieveTestResponseInternal

    fun fetchTestData(){
        retrieveTestResponseInternal.postValue(true)
        viewModelScope.launch (Dispatchers.IO){
            repository.test {
                retrieveTestResponseInternal.postValue(it)
            }
        }
    }

}