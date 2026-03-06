package com.example.taskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_logs")
data class StepsLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: Long, // Date in milliseconds
    val steps: Int, // Number of steps
    val timestamp: Long = System.currentTimeMillis()
)