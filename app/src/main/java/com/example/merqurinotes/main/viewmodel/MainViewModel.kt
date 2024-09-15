package com.example.merqurinotes.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merqurinotes.base.BaseViewModel
import com.example.merqurinotes.base.model.SummarizeData
import com.example.merqurinotes.main.repository.MainRepository
import com.example.merqurinotes.room.Category
import com.example.merqurinotes.room.Content
import com.example.merqurinotes.utils.api.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(repository: MainRepository) : BaseViewModel<MainRepository>(repository){

    private val retrieveCategoryListResponseInternal = MutableLiveData<ApiResource<List<Content>>>()
    val retrieveCategoryListResponse : MutableLiveData<ApiResource<List<Content>>>
        get() = retrieveCategoryListResponseInternal

    private val retrieveWorkStudyListResponseInternal = MutableLiveData<ApiResource<List<Content>>>()
    val retrieveWorkStudyListResponse : MutableLiveData<ApiResource<List<Content>>>
        get() = retrieveWorkStudyListResponseInternal

    private val retrieveLifeListResponseInternal = MutableLiveData<ApiResource<List<Content>>>()
    val retrieveLifeListResponse : MutableLiveData<ApiResource<List<Content>>>
        get() = retrieveLifeListResponseInternal

    private val retrieveHealthListResponseInternal = MutableLiveData<ApiResource<List<Content>>>()
    val retrieveHealthListResponse : MutableLiveData<ApiResource<List<Content>>>
        get() = retrieveHealthListResponseInternal

    private val retrieveSummarizeCategoryListResponseInternal = MutableLiveData<ApiResource<List<SummarizeData>>>()
    val retrieveSummarizeCategoryListResponse : MutableLiveData<ApiResource<List<SummarizeData>>>
        get() = retrieveSummarizeCategoryListResponseInternal

    fun fetchAllContentFromDB(){
        retrieveCategoryListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchAllContentFromDB {
                retrieveCategoryListResponseInternal.postValue(it)
            }
        }
    }

    fun fetchSummarizeContentFromDB(){
        retrieveSummarizeCategoryListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchSummarizeContentFromDB {
                retrieveSummarizeCategoryListResponseInternal.postValue(it)
            }
        }
    }

    fun fetchWorkStudyContentFromDB(){
        retrieveWorkStudyListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchWorkStudyContentFromDB {
                retrieveWorkStudyListResponseInternal.postValue(it)
            }
        }
    }

    fun fetchLifeContentFromDB(){
        retrieveLifeListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchLifeContentFromDB {
                retrieveLifeListResponseInternal.postValue(it)
            }
        }
    }

    fun fetchHealthContentFromDB(){
        retrieveHealthListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch (Dispatchers.IO){
            repository.fetchHealthContentFromDB {
                retrieveHealthListResponseInternal.postValue(it)
            }
        }
    }
}