package com.example.merqurinotes.splashscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.merqurinotes.base.BaseViewModel
import com.example.merqurinotes.room.Category
import com.example.merqurinotes.splashscreen.repository.SplashScreenRepository
import com.example.merqurinotes.utils.api.ApiResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(repository: SplashScreenRepository) :
    BaseViewModel<SplashScreenRepository>(repository) {

    private val retrieveInsertCategoryToDBResponseInternal = MutableLiveData<ApiResource<Boolean>>()
    val retrieveInsertCategoryToDBResponse: LiveData<ApiResource<Boolean>?>
        get() = retrieveInsertCategoryToDBResponseInternal

    private val retrieveCategoryListResponseInternal =
        MutableLiveData<ApiResource<List<Category>>>()
    val retrieveCategoryListResponse: MutableLiveData<ApiResource<List<Category>>>
        get() = retrieveCategoryListResponseInternal

    fun insertCategoryToDB() {
        retrieveInsertCategoryToDBResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategoryToDB {
                retrieveInsertCategoryToDBResponseInternal.postValue(it)
            }
        }
    }

    fun fetchCategoryListFromDB() {
        retrieveCategoryListResponseInternal.postValue(ApiResource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchCategoryListFromDB {
                retrieveCategoryListResponseInternal.postValue(it)
            }
        }
    }

}