package com.example.merqurinotes.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merqurinotes.base.BaseViewModel
import com.example.merqurinotes.notes.common.response.RetrieveCategoryListResponse
import com.example.merqurinotes.notes.repository.AddNotesRepository
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.splashscreen.repository.SplashScreenRepository
import com.example.merqurinotes.utils.api.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddNotesViewModel @Inject constructor(repository: AddNotesRepository) : BaseViewModel<AddNotesRepository>(repository){

    private val retrieveListCategoryResponseInternal = MutableLiveData<ApiResource<RetrieveCategoryListResponse>>()
    val retrieveListCategoryResponse : LiveData<ApiResource<RetrieveCategoryListResponse>?>
        get() = retrieveListCategoryResponseInternal

    private val retrieveSaveNotesResponseInternal = MutableLiveData<ApiResource<Boolean>>()
    val retrieveSaveNotesResponse : LiveData<ApiResource<Boolean>?>
        get() = retrieveSaveNotesResponseInternal

    fun fetchListCategoryFromAPI(){
        retrieveListCategoryResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchListCategoryFromAPI {
                retrieveListCategoryResponseInternal.postValue(it)
            }
        }
    }

    fun saveNotesToDB(content: Content){
        retrieveSaveNotesResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.saveNotesToDB(content) {
                retrieveSaveNotesResponseInternal.postValue(it)
            }
        }
    }
}