package com.example.mymoney.data.model

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymoney.data.COSTS_TABLE
import java.util.*

@Entity(tableName = COSTS_TABLE)
data class Cost(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val id: Int,
    @ColumnInfo(index = true)
    var typeId: Int,
    var cost: Float,
    var description: String = "",
    var buyDate: Date = Date()
)