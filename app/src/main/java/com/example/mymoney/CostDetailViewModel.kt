package com.example.mymoney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.mymoney.data.MoneyRepository
import com.example.mymoney.data.model.Cost

class CostDetailViewModel(): ViewModel() {
    private val dbRepository = MoneyRepository.get()
    private val costIdLiveData = MutableLiveData<Int>()
    var costLiveData : LiveData<Cost?> =
        costIdLiveData.switchMap { costId ->
            dbRepository.getCost(costId)
        }
    fun loadCost(id: Int) {
        costIdLiveData.value = id
    }
    val costTypesLiveData = dbRepository.getAllTypes()
    fun saveCost(cost: Cost) = dbRepository.saveCost(cost)
    fun killCost(cost: Cost) = dbRepository.killCost(cost)
}