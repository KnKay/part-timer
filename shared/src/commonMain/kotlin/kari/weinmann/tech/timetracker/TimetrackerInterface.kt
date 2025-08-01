package kari.weinmann.tech.timetracker

interface TimetrackerInterface {
    suspend fun whoAmI():UserInfo
}