package com.example.taskmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class HealthFragment : Fragment() {

    private lateinit var waterProgressBar: ProgressBar
    private lateinit var stepsProgressBar: ProgressBar
    private lateinit var waterAmountText: TextView
    private lateinit var stepsAmountText: TextView
    private lateinit var inputSteps: TextInputEditText
    private lateinit var btnAddWater250: Button
    private lateinit var btnAddWater500: Button
    private lateinit var btnResetWater: Button
    private lateinit var btnAddSteps: Button

    private var todayWaterMl = 0
    private var todaySteps = 0
    private val waterGoalMl = 2000
    private val stepsGoal = 10000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.health_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // Initialize views
            waterProgressBar = view.findViewById(R.id.water_progress)
            stepsProgressBar = view.findViewById(R.id.steps_progress)
            waterAmountText = view.findViewById(R.id.water_amount)
            stepsAmountText = view.findViewById(R.id.steps_amount)
            inputSteps = view.findViewById(R.id.input_steps)
            btnAddWater250 = view.findViewById(R.id.btn_add_water_250)
            btnAddWater500 = view.findViewById(R.id.btn_add_water_500)
            btnResetWater = view.findViewById(R.id.btn_reset_water)
            btnAddSteps = view.findViewById(R.id.btn_add_steps)

            setupListeners()
            updateUI()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        btnAddWater250.setOnClickListener {
            addWater(250)
            Toast.makeText(requireContext(), "Added 250ml", Toast.LENGTH_SHORT).show()
        }

        btnAddWater500.setOnClickListener {
            addWater(500)
            Toast.makeText(requireContext(), "Added 500ml", Toast.LENGTH_SHORT).show()
        }

        btnResetWater.setOnClickListener {
            todayWaterMl = 0
            updateUI()
            Toast.makeText(requireContext(), "Water reset", Toast.LENGTH_SHORT).show()
        }

        btnAddSteps.setOnClickListener {
            val stepsStr = inputSteps.text.toString().trim()
            if (stepsStr.isNotEmpty()) {
                try {
                    val steps = stepsStr.toInt()
                    addSteps(steps)
                    inputSteps.text?.clear()
                    Toast.makeText(requireContext(), "Added $steps steps", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Invalid number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Enter steps", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addWater(mlAmount: Int) {
        todayWaterMl += mlAmount
        if (todayWaterMl > waterGoalMl) {
            todayWaterMl = waterGoalMl
        }
        updateUI()
    }

    private fun addSteps(steps: Int) {
        todaySteps += steps
        if (todaySteps > stepsGoal) {
            todaySteps = stepsGoal
        }
        updateUI()
    }

    private fun updateUI() {
        // Update water
        val waterPercent = (todayWaterMl * 100) / waterGoalMl
        waterProgressBar.progress = waterPercent.coerceIn(0, 100)
        waterAmountText.text = String.format("%.1f", todayWaterMl / 1000.0) + "L / 2.0L"

        // Update steps
        val stepsPercent = (todaySteps * 100) / stepsGoal
        stepsProgressBar.progress = stepsPercent.coerceIn(0, 100)
        stepsAmountText.text = "$todaySteps / $stepsGoal"
    }
}