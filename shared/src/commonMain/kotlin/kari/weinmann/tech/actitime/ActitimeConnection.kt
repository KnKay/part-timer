package kari.weinmann.tech.actitime

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import kari.weinmann.tech.timetracker.*

class ActitimeConnection(val client: HttpClient):TimetrackerInterface {
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
}
