package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.lifecycle.LiveData

@Dao
interface WaterLogDao {

    // Insert water log
    @Insert
    suspend fun insertWaterLog(waterLog: WaterLog)

    // Get all water logs
    @Query("SELECT * FROM water_logs ORDER BY date DESC")
    fun getAllWaterLogs(): LiveData<List<WaterLog>>

    // Get water logs for a specific date
    @Query("SELECT * FROM water_logs WHERE date = :date ORDER BY timestamp DESC")
    fun getWaterLogsByDate(date: Long): LiveData<List<WaterLog>>

    // Get total water for today
    @Query("SELECT SUM(amountMl) FROM water_logs WHERE date = :date")
    fun getTodayWaterTotal(date: Long): LiveData<Int>

    // Delete water log
    @Query("DELETE FROM water_logs WHERE id = :id")
    suspend fun deleteWaterLog(id: Int)
}