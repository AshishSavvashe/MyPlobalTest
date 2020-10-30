package com.rjp.assignments.viewmodel

import ResultDataRepository
import androidx.lifecycle.*
import com.aps.assignment.model.ResponseModel


/*
* Author:Ashish Savvashe
* */

class ResultDataModel: ViewModel() {

    private val mRepository: ResultDataRepository = ResultDataRepository()

    fun getFactsList(): MutableLiveData<ResponseModel> {
        return mRepository.getFacts()
    }

}
