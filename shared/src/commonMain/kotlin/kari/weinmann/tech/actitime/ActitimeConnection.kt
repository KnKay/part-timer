package kari.weinmann.tech.actitime

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kari.weinmann.tech.actitime.entities.ActiTimeTrack
import kari.weinmann.tech.actitime.entities.TaskRecord
import kari.weinmann.tech.actitime.entities.TimeDelta
import kari.weinmann.tech.timetracker.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.*
import kotlinx.serialization.json.*


class ActitimeConnection(val client: HttpClient,):TimetrackerInterface {
     lateinit var user: UserInfo
     init {
         user = runBlocking {
             val UserInfo = client.get("users/me").body<UserInfo>()
             return@runBlocking UserInfo
         }
     }
    companion object Factory {
         fun createInstance(
            baseUrl: String,
            user: String,
            password: String,
        ): ActitimeConnection {
            val httpClient = HttpClient() {
                expectSuccess = true
                defaultRequest {
                    url("$baseUrl/api/v1/")
                }

                install(ContentNegotiation) {
                    json()
                }
                install(Auth) {

                    basic {
                        credentials {
                            BasicAuthCredentials(username = user,password =  password)
                        }

                    }
                }
            }

            return ActitimeConnection(httpClient)
        }
    }

    override suspend fun whoAmI(): UserInfo = client.get("users/me").body()
    override suspend fun getGroups(): List<Group> = client.get("customers").body<ApiRessource<Group>>().items!!
    override suspend fun getProjects(): List<Project> = client.get("projects").body<ApiRessource<Project>>().items!!
    override suspend fun getProjectTasks(projectId: Int): List<Task> =client.get("tasks"){
        url {
            parameters.append("projectIds", "$projectId")
        }
    }.body<ApiRessource<Task>>().items!!

    override suspend fun getTasks(): List<Task> = client.get("tasks").body<ApiRessource<Task>>().items!!
    override suspend fun getSpentTime(from: LocalDate): PeriodSpending {
        val track = client.get("timetrack"){
            url{
                parameters.append("dateFrom", from.toString())
            }
        }.body<ActiTimeTrack>()
        var minutes = 0
        for (day in track.data){
            for (record in day.records){
                minutes+=record.time
            }
        }
        return  PeriodSpending (from, minutes)
    }

    override suspend fun getTaskTime(date: LocalDate, taskId: Int): Int = client.get("timetrack/${user.id}/${date}/${taskId}").body<TaskRecord>().time

    override suspend fun addTime(taskId: Int, minutes: Int, date: LocalDate) {
        //We need to get time first
        val actual = getTaskTime(date, taskId)
        val patched = client.patch("timetrack/${user.id}/${date}/${taskId}/time"){
            contentType(ContentType.Application.Json)
          setBody(TimeDelta(actual+minutes))
        }.body<TaskRecord>()
    }
}
