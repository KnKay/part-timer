package kari.weinmann.tech.timetracker

interface TimetrackerInterface {
    suspend fun whoAmI():UserInfo
    suspend fun getGroups():List<Group>
    suspend fun getProjects():List<Project>
    suspend fun getProjectTasks(projectId: Int):List<Task>
    suspend fun getTasks():List<Task>
}