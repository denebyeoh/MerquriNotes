package com.example.merqurinotes.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merqurinotes.base.BaseViewModel
import com.example.merqurinotes.settings.repository.SettingsRepository
import com.example.merqurinotes.utils.api.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SettingsViewModel @Inject constructor(repository: SettingsRepository) : BaseViewModel<SettingsRepository>(repository){

    private val retrieveDeleteAllDataResponseInternal = MutableLiveData<ApiResource<Boolean>>()
    val retrieveDeleteAllDataResponse : LiveData<ApiResource<Boolean>?>
        get() = retrieveDeleteAllDataResponseInternal

    fun deleteAllContent(){
        retrieveDeleteAllDataResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllContent() {
                retrieveDeleteAllDataResponseInternal.postValue(it)
            }
        }
    }

}