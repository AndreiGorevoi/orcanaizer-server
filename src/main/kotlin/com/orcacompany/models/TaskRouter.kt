package com.orcacompany.models

import com.orcacompany.dao.mongo.taskMongoDao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.taskRouting() {
    route("task") {
        get() {
            val tasks = taskMongoDao.allTask()
            if (tasks.isNotEmpty()) {
                call.respond(tasks)
            } else {
                call.respondText(text = "No task for today yet", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val taskId = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val task = taskMongoDao.task(taskId) ?: return@get call.respondText(
                text = "No task with $taskId",
                status = HttpStatusCode.NotFound
            )
            call.respond(task)
        }

        get("priority/{priority}") {
            val priorityNumber = call.parameters["priority"] ?: return@get call.respondText(
                text = "Missing priority",
                status = HttpStatusCode.BadRequest
            )
//            TODO: replace with select from DB
            val listOfTask = taskMongoDao.allTask().filter { it.priority == Priority.priorityFromInt(priorityNumber.toInt()) }
            if (listOfTask.isEmpty()) {
                call.respondText(text = "There is not task with $priorityNumber", status = HttpStatusCode.NotFound)
            } else {
                call.respond(listOfTask)
            }
        }

        post {
            val newTask = call.receive<Task>()
            taskMongoDao.addNewTask(newTask)
            call.respondText("Task is added", status = HttpStatusCode.OK)
        }

        delete("/{id}") {
            val taskId = call.parameters["id"] ?: error("Invalid delete request")
            taskMongoDao.deleteTask(taskId)
            call.respond(HttpStatusCode.OK)
        }
    }
}