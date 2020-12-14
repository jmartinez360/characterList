package com.android.lidlapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract val loadingLiveData: LiveData<Boolean>
    abstract val errorLiveData: LiveData<Boolean>

    companion object {
        const val SHOW = true
        const val HIDE = false
    }
}