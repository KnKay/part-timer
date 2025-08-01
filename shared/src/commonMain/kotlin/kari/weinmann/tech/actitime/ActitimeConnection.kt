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
import kari.weinmann.tech.timetracker.TimetrackerInterface
import kari.weinmann.tech.timetracker.UserInfo

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
                    url(baseUrl)
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

    override suspend fun whoAmI(): UserInfo = client.get("/api/v1/users/me").body()

}
