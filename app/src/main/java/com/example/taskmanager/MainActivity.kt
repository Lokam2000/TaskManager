package com.example.taskmanager

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

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
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)

        val inputTitle = dialogView.findViewById<TextInputEditText>(R.id.input_task_title)
        val spinnerCategory = dialogView.findViewById<Spinner>(R.id.spinner_category)
        val spinnerPriority = dialogView.findViewById<Spinner>(R.id.spinner_priority)
        val inputDate = dialogView.findViewById<TextInputEditText>(R.id.input_date)
        val inputTime = dialogView.findViewById<TextInputEditText>(R.id.input_time)
        val inputDuration = dialogView.findViewById<TextInputEditText>(R.id.input_duration)

        // Setup Category Spinner
        val categories = arrayOf("Work", "Personal", "Shopping", "Health", "Finance")
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        // Setup Priority Spinner
        val priorities = arrayOf("HIGH", "MEDIUM", "LOW")
        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = priorityAdapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btn_save).setOnClickListener {
            val title = inputTitle.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString()
            val priority = spinnerPriority.selectedItem.toString()
            val dateStr = inputDate.text.toString().trim()
            val timeStr = inputTime.text.toString().trim()
            val durationStr = inputDuration.text.toString().trim()

            if (title.isEmpty() || dateStr.isEmpty() || timeStr.isEmpty() || durationStr.isEmpty()) {
                android.widget.Toast.makeText(this, "Please fill all fields", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                val date = dateFormat.parse(dateStr)
                val duration = durationStr.toInt()

                val newTask = Task(
                    title = title,
                    category = category,
                    priority = priority,
                    dueDate = date?.time ?: System.currentTimeMillis(),
                    dueTime = timeStr,
                    duration = duration,
                    isCompleted = false
                )

                viewModel.insertTask(newTask)
                dialog.dismiss()
                android.widget.Toast.makeText(this, "Task added!", android.widget.Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                android.widget.Toast.makeText(this, "Invalid date format", android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun completeTask(task: Task) {
        viewModel.completeTask(task)
    }

    private fun deleteTask(task: Task) {
        viewModel.deleteTask(task)
    }
}