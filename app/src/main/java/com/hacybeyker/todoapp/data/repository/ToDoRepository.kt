package com.hacybeyker.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.hacybeyker.todoapp.data.ToDoDao
import com.hacybeyker.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()
    suspend fun insertData(toDoData: ToDoData) = toDoDao.insertData(toDoData)
    suspend fun updateData(toDoData: ToDoData) = toDoDao.updateData(toDoData)
    suspend fun deleteData(toDoData: ToDoData) = toDoDao.deleteData(toDoData)
    suspend fun deleteAllData() = toDoDao.deleteAllData()
    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>> = toDoDao.searchDataBase(searchQuery)
}