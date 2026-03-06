package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.lifecycle.LiveData

@Dao
interface TaskDao {

    // Insert a new task
    @Insert
    suspend fun insertTask(task: Task)

    // Get all tasks
    @Query("SELECT * FROM tasks ORDER BY dueDate ASC, dueTime ASC")
    fun getAllTasks(): LiveData<List<Task>>

    // Get tasks by date
    @Query("SELECT * FROM tasks WHERE dueDate = :date ORDER BY dueTime ASC")
    fun getTasksByDate(date: Long): LiveData<List<Task>>

    // Get tasks by category
    @Query("SELECT * FROM tasks WHERE category = :category ORDER BY dueDate ASC")
    fun getTasksByCategory(category: String): LiveData<List<Task>>

    // Get completed tasks
    @Query("SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY dueDate ASC")
    fun getCompletedTasks(): LiveData<List<Task>>

    // Get pending tasks
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY dueDate ASC")
    fun getPendingTasks(): LiveData<List<Task>>

    // Update a task
    @Update
    suspend fun updateTask(task: Task)

    // Delete a task
    @Delete
    suspend fun deleteTask(task: Task)

    // Delete all tasks
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}