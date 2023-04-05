package com.example.mymoney.data

import android.text.Editable


internal const val TYPES_TABLE = "types"
internal const val COSTS_TABLE = "costs"
internal const val DATABASE_NAME = "MoneyDatabase"

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)