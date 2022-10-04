package com.orcacompany.dao

import com.orcacompany.models.Task

interface TaskDaoFacade {
    suspend fun allTask(): List<Task>
    suspend fun task(id: String): Task?
    suspend fun addNewTask(taskData: Task): Result<Task?>
    suspend fun deleteTask(taskId: String): Boolean
}