package com.example.taskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_logs")
data class WaterLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: Long, // Date in milliseconds
    val amountMl: Int, // Amount in milliliters (250ml per glass)
    val timestamp: Long = System.currentTimeMillis()
)