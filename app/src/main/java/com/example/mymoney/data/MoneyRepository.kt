package com.example.mymoney.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.room.Room
import com.example.mymoney.data.model.Cost
import java.util.concurrent.Executors
import kotlin.math.cos

class MoneyRepository private constructor(context: Context) {
    private val database : MoneyDatabase = Room.databaseBuilder(
        context.applicationContext,
        MoneyDatabase::class.java,
        DATABASE_NAME).build()
    private val executor = Executors.newSingleThreadExecutor()
    private val moneyDAO = database.moneyDAO()

    fun getAllCosts() = moneyDAO.getAllCosts()
    /*fun getCost(id: Int): LiveData<Cost?> {
        val res = moneyDAO.getCost(id)
        return if (res != null) res else {
            liveData {
                emit(Cost(0, 0, 0F, "",))
            }
        }
    }*/
    fun getCost(id: Int) = moneyDAO.getCost(id)
    fun getAllTypes() = moneyDAO.getAllTypes()
    fun addCost(cost: Cost) = executor.execute {
        moneyDAO.addCost(cost)
    }

    fun saveCost(cost: Cost) = executor.execute {
        if (cost.id > 0) moneyDAO.saveCost(cost)
        else moneyDAO.addCost(cost)
    }
    fun killCost(cost: Cost) = executor.execute {
        moneyDAO.killCost(cost)
    }

    companion object {
        private var INSTANCE: MoneyRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MoneyRepository(context)
            }
        }
        fun get() : MoneyRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository should be initialized!")
        }
    }
}