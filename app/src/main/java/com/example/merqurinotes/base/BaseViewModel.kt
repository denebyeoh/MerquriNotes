package com.example.merqurinotes.base

import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel<R: BaseRepository> (protected val repository: R): AndroidViewModel(repository.ctxObj)