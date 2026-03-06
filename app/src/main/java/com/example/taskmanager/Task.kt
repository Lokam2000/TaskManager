package com.example.taskmanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String = "",
    val category: String, // Work, Personal, Shopping, Health, Finance
    val priority: String, // HIGH, MEDIUM, LOW
    val dueDate: Long, // Stored as milliseconds
    val dueTime: String, // Format: "HH:mm" (e.g., "14:30")
    val duration: Int = 30, // Duration in minutes
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)