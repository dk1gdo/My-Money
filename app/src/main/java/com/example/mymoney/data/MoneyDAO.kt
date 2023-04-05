package com.example.mymoney.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymoney.data.model.Cost
import com.example.mymoney.data.model.CostType

@Dao
interface MoneyDAO {
    @Query("SELECT * FROM $TYPES_TABLE")
    fun getAllTypes():LiveData<List<CostType>>

    @Query("SELECT * FROM $COSTS_TABLE")
    fun getAllCosts():LiveData<List<Cost>>
    @Query("SELECT * FROM $COSTS_TABLE WHERE _id=:id")
    fun getCost(id: Int): LiveData<Cost?>
    @Insert
    fun addCost(cost: Cost)
    @Update
    fun saveCost(cost: Cost)
    @Delete
    fun killCost(cost: Cost)
}