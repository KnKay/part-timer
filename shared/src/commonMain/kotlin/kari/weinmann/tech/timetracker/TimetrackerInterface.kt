package kari.weinmann.tech.timetracker

import kotlinx.datetime.LocalDate

interface TimetrackerInterface {
    suspend fun whoAmI():UserInfo
    suspend fun getGroups():List<Group>
    suspend fun getProjects():List<Project>
    suspend fun getProjectTasks(projectId: Int):List<Task>
    suspend fun getTasks():List<Task>
    suspend fun getSpentTime(from: LocalDate): PeriodSpending
    suspend fun getTaskTime(date:LocalDate, taskId: Int): Int
    suspend fun addTime(taskId: Int, minutes: Int, date: LocalDate)
}
