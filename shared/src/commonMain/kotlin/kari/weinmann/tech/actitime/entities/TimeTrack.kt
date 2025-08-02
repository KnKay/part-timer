package kari.weinmann.tech.actitime.entities

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class ActiTimeTrack(
    val dateFrom: String,
    val dateTo: String,
    val data: List<DayRecord>

)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class DayRecord(
    val userId: Int,
    val date: String,
    val records: List<TaskRecord>
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class TaskRecord(
    val taskId: Int,
    val time: Int
)

@Serializable
data class TimeDelta(
    val delta: Int
)