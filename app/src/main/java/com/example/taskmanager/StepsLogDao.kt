package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.lifecycle.LiveData

@Dao
interface StepsLogDao {

    // Insert steps log
    @Insert
    suspend fun insertStepsLog(stepsLog: StepsLog)

    // Get all steps logs
    @Query("SELECT * FROM steps_logs ORDER BY date DESC")
    fun getAllStepsLogs(): LiveData<List<StepsLog>>

    // Get steps logs for a specific date
    @Query("SELECT * FROM steps_logs WHERE date = :date ORDER BY timestamp DESC")
    fun getStepsLogsByDate(date: Long): LiveData<List<StepsLog>>

    // Get total steps for today
    @Query("SELECT SUM(steps) FROM steps_logs WHERE date = :date")
    fun getTodayStepsTotal(date: Long): LiveData<Int>

    // Delete steps log
    @Query("DELETE FROM steps_logs WHERE id = :id")
    suspend fun deleteStepsLog(id: Int)
}