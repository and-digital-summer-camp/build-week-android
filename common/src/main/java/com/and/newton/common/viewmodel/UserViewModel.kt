package com.and.newton.common.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*


//Shared Usermodel
class UserViewModel @ViewModelInject constructor() : ViewModel() {

    //Needs to change to user object
    private var _user = MutableLiveData<String>()
    val user: LiveData<String>
        get() = _user

    fun updateUser(){
        _user.postValue("hello")
    }


}