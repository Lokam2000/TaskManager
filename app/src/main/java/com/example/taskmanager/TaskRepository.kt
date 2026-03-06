package com.example.taskmanager

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val taskDao: TaskDao) {

    // LiveData for all tasks
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    // Insert a new task
    suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task)
        }
    }

    // Get tasks for a specific date
    fun getTasksByDate(date: Long): LiveData<List<Task>> {
        return taskDao.getTasksByDate(date)
    }

    // Get tasks by category
    fun getTasksByCategory(category: String): LiveData<List<Task>> {
        return taskDao.getTasksByCategory(category)
    }

    // Get completed tasks
    fun getCompletedTasks(): LiveData<List<Task>> {
        return taskDao.getCompletedTasks()
    }

    // Get pending tasks
    fun getPendingTasks(): LiveData<List<Task>> {
        return taskDao.getPendingTasks()
    }

    // Update a task
    suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    // Delete a task
    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }

    // Delete all tasks
    suspend fun deleteAllTasks() {
        withContext(Dispatchers.IO) {
            taskDao.deleteAllTasks()
        }
    }
}