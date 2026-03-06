package com.example.taskmanager

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var taskListRecyclerView: RecyclerView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var tabSchedule: Button
    private lateinit var tabTasks: Button
    private lateinit var tabHealth: Button

    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    private var currentTab = "schedule"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskListRecyclerView = findViewById(R.id.task_list)
        fabAddTask = findViewById(R.id.fab_add_task)
        tabSchedule = findViewById(R.id.tab_schedule)
        tabTasks = findViewById(R.id.tab_tasks)
        tabHealth = findViewById(R.id.tab_health)

        setupRecyclerView()
        setupFAB()
        setupTabs()
        observeTasks()
        addSampleTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task -> completeTask(task) },
            onDeleteClick = { task -> deleteTask(task) }
        )

        taskListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }

    private fun observeTasks() {
        viewModel.allTasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
        }
    }

    private fun setupFAB() {
        fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun setupTabs() {
        tabSchedule.setBackgroundResource(R.drawable.gradient_header)
        tabSchedule.setTextColor(getColor(android.R.color.white))

        tabSchedule.setOnClickListener {
            switchTab("schedule", tabSchedule, tabTasks, tabHealth)
        }

        tabTasks.setOnClickListener {
            switchTab("tasks", tabTasks, tabSchedule, tabHealth)
        }

        tabHealth.setOnClickListener {
            switchTab("health", tabHealth, tabSchedule, tabTasks)
        }
    }

    private fun switchTab(tabName: String, activeTab: Button, inactiveTab1: Button, inactiveTab2: Button) {
        currentTab = tabName

        activeTab.setBackgroundResource(R.drawable.gradient_header)
        activeTab.setTextColor(getColor(android.R.color.white))

        inactiveTab1.setBackgroundColor(getColor(android.R.color.white))
        inactiveTab1.setTextColor(getColor(R.color.text_light))

        inactiveTab2.setBackgroundColor(getColor(android.R.color.white))
        inactiveTab2.setTextColor(getColor(R.color.text_light))
    }

    private fun addSampleTasks() {
        val today = Calendar.getInstance().timeInMillis

        val sampleTasks = listOf(
            Task(
                title = "Design UI mockups",
                category = "Work",
                priority = "HIGH",
                dueDate = today,
                dueTime = "10:00",
                duration = 60,
                isCompleted = false
            ),
            Task(
                title = "Complete project report",
                category = "Work",
                priority = "HIGH",
                dueDate = today,
                dueTime = "14:30",
                duration = 90,
                isCompleted = true
            ),
            Task(
                title = "Buy groceries",
                category = "Shopping",
                priority = "MEDIUM",
                dueDate = today,
                dueTime = "18:00",
                duration = 45,
                isCompleted = false
            ),
            Task(
                title = "Evening walk",
                category = "Health",
                priority = "LOW",
                dueDate = today,
                dueTime = "19:00",
                duration = 30,
                isCompleted = false
            ),
            Task(
                title = "Team standup meeting",
                category = "Work",
                priority = "HIGH",
                dueDate = today,
                dueTime = "11:00",
                duration = 30,
                isCompleted = false
            ),
            Task(
                title = "Pay electricity bill",
                category = "Finance",
                priority = "HIGH",
                dueDate = today,
                dueTime = "15:00",
                duration = 15,
                isCompleted = false
            )
        )

        sampleTasks.forEach { task ->
            viewModel.insertTask(task)
        }
    }

    private fun showAddTaskDialog() {
        // TODO: Implement add task dialog
    }

    private fun completeTask(task: Task) {
        viewModel.completeTask(task)
    }

    private fun deleteTask(task: Task) {
        viewModel.deleteTask(task)
    }
}