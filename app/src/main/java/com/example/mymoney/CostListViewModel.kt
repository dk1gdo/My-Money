package com.example.mymoney

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymoney.data.MoneyRepository
import com.example.mymoney.data.model.Cost

class CostListViewModel : ViewModel() {
    private val dbRepository = MoneyRepository.get()
    val costsLiveData: LiveData<List<Cost>> = dbRepository.getAllCosts()
}