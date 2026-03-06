package com.example.taskmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, onTaskClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    class TaskViewHolder(
        itemView: View,
        private val onTaskClick: (Task) -> Unit,
        private val onDeleteClick: (Task) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val checkboxTask: CheckBox = itemView.findViewById(R.id.checkbox_task)
        private val titleTask: TextView = itemView.findViewById(R.id.title_task)
        private val timeTask: TextView = itemView.findViewById(R.id.time_task)
        private val durationTask: TextView = itemView.findViewById(R.id.duration_task)
        private val categoryTask: TextView = itemView.findViewById(R.id.category_task)
        private val priorityTask: TextView = itemView.findViewById(R.id.priority_task)

        fun bind(task: Task) {
            titleTask.text = task.title
            timeTask.text = "🕐 ${task.dueTime}"
            durationTask.text = "⏱️ ${task.duration} mins"
            categoryTask.text = task.category
            priorityTask.text = task.priority

            // Set checkbox state
            checkboxTask.isChecked = task.isCompleted

            // Change appearance if completed
            if (task.isCompleted) {
                titleTask.alpha = 0.6f
                titleTask.paintFlags = titleTask.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleTask.alpha = 1f
                titleTask.paintFlags = titleTask.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Checkbox click listener
            checkboxTask.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked != task.isCompleted) {
                    onTaskClick(task.copy(isCompleted = isChecked))
                }
            }

            // Long-press to delete
            itemView.setOnLongClickListener {
                onDeleteClick(task)
                true
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}