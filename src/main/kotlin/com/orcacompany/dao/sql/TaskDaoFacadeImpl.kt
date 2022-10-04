//package com.orcacompany.dao.sql
//
//import com.orcacompany.dao.TaskDaoFacade
//import com.orcacompany.dao.sql.DataBaseFactory.dbQuery
//import com.orcacompany.models.Task
//import com.orcacompany.models.Tasks
//import org.jetbrains.exposed.sql.*
//
//class TaskDaoFacadeImpl : TaskDaoFacade {
//    private fun resultRowToTask(row: ResultRow) = Task(
//        id = row[Tasks.id],
//        title = row[Tasks.title],
//        description = row[Tasks.description],
//        priority = row[Tasks.priority],
//        dueDate = row[Tasks.dueDate],
//        userId = row[Tasks.userId],
//        completed = row[Tasks.completed]
//    )
//
//
//    override suspend fun allTask(): List<Task> = dbQuery {
//        Tasks.selectAll().map(::resultRowToTask)
//    }
//
//    override suspend fun task(id: Int): Task? = dbQuery {
//        Tasks.select { Tasks.id eq id }
//            .map(::resultRowToTask)
//            .singleOrNull()
//    }
//
//    override suspend fun addNewTask(taskData: Task): Result<Task?> = dbQuery {
//        val insertStatement = Tasks.insert {
//            it[title] = taskData.title
//            it[description] = taskData.description
//            it[priority] = taskData.priority
//            it[dueDate] = taskData.dueDate
//            it[userId] = taskData.userId
//            it[completed] = taskData.completed
//        }
//        Result.success(insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTask))
//    }
//
//    override suspend fun deleteTask(taskId: String): Boolean = dbQuery {
//        true
//    }
//
//}
//
//val taskDao = TaskDaoFacadeImpl()