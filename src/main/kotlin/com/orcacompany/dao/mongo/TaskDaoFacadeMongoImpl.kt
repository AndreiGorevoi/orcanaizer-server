package com.orcacompany.dao.mongo

import com.orcacompany.dao.TaskDaoFacade
import com.orcacompany.models.Task
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import java.util.*


class TaskDaoFacadeMongoImpl: TaskDaoFacade {
    override suspend fun allTask(): List<Task> {
        return collection.find().toList()
    }

    override suspend fun task(id: String): Task? {
//        TODO: replace from db search
        return collection.find().toList().find { it.id == id }
    }

    override suspend fun addNewTask(taskData: Task): Result<Task?> {
        collection.insertOne(taskData)
        return Result.success(taskData)
    }

    override suspend fun deleteTask(taskId: String): Boolean {
        collection.deleteOne(Task::id eq taskId)
        return true
    }

}

val client = KMongo.createClient().coroutine
val database = client.getDatabase("taskList")
val collection = database.getCollection<Task>()

val taskMongoDao = TaskDaoFacadeMongoImpl()