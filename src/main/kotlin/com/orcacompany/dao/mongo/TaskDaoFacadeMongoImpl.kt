package com.orcacompany.dao.mongo

import com.orcacompany.dao.TaskDaoFacade
import com.orcacompany.models.Priority
import com.orcacompany.models.Task
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo


class TaskDaoFacadeMongoImpl: TaskDaoFacade {
    override suspend fun allTask(): List<Task> {
        return collection.find(Task::removed eq false).toList()
    }

    override suspend fun getTask(priority: Int): List<Task> {
        return collection
            .find(Task::removed eq false).toList().filter { it.priority==Priority.priorityFromInt(priority) }
    }

    override suspend fun task(id: String): Task? {
        return collection.find(Task::id eq id).toList().first()
    }

    override suspend fun addNewTask(taskData: Task): Result<Task?> {
        collection.insertOne(taskData)
        return Result.success(taskData)
    }

    override suspend fun deleteTask(taskId: String): Boolean {
        collection.deleteOne(Task::id eq taskId)
        return true
    }

    override suspend fun updateTask(taskData: Task): Boolean {
        collection.updateOne(Task::id eq taskData.id, taskData)
        return true
    }

}

val client = KMongo.createClient().coroutine
val database = client.getDatabase("taskList")
val collection: CoroutineCollection<Task> = database.getCollection<Task>()

val taskMongoDao = TaskDaoFacadeMongoImpl()