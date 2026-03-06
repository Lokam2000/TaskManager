package com.example.taskmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    private var currentDate: Long = System.currentTimeMillis()
    private var currentCategory: String? = null

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    // Insert a new task
    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    // Get tasks by date
    fun getTasksByDate(date: Long): LiveData<List<Task>> {
        currentDate = date
        return repository.getTasksByDate(date)
    }

    // Get tasks by category
    fun getTasksByCategory(category: String): LiveData<List<Task>> {
        currentCategory = category
        return repository.getTasksByCategory(category)
    }

    // Get completed tasks
    fun getCompletedTasks(): LiveData<List<Task>> {
        return repository.getCompletedTasks()
    }

    // Get pending tasks
    fun getPendingTasks(): LiveData<List<Task>> {
        return repository.getPendingTasks()
    }

    // Update a task
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    // Delete a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    // Mark task as completed
    fun completeTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = true))
        }
    }

    // Delete all tasks
    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}