package rhea.group.app.models

import android.annotation.SuppressLint
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat

private val gson = Gson()

data class StageSession(
    val id: String = "",
    val additionalBreathworkSession: String? = "",
    val completedAdditionalBreathworkSessions: String? = "", // null
    val isCompleted: Boolean = false,
    val isPending: Boolean = false,
    val isActive: Boolean = false,
    val completionDate: String = "",
    val updateDate: Int = 0,
    val category: Category = Category.Base,
    val feeling: FeelingsType? = null,
    val recommendedTime: String? = "",
    val session: String? = "",
    val equipments: List<String>? = emptyList(),
    val sleepQuestions: List<SleepQuestion>? = emptyList()
) {
    companion object {
        fun fromJson(json: String): StageSession = gson.fromJson(json, StageSession::class.java)
    }

    fun toJson(): String = gson.toJson(this)

    @SuppressLint("SimpleDateFormat")
    fun epochToDate(): String {
        val date = java.util.Date(updateDate * 1000L)
        val sdf = SimpleDateFormat("MMM dd . hh:mm")
        return sdf.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToEpoch(): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val d = sdf.parse(completionDate)
        return (d?.time ?: 1000) / 1000
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToFormatMMMdd(): String {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(completionDate)
            val sdf = SimpleDateFormat("MMM dd . hh:mm")

            return sdf.format(date!!)
        } catch (e: ParseException) {
            ""
        }
    }
}
