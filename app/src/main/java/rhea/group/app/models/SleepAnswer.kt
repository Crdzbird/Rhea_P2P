package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class SleepAnswer(
    @SerializedName("answers")
    val answers: Map<String, Boolean?> = emptyMap()
) {
    companion object {
        fun fromJson(json: String): SleepAnswer = gson.fromJson(json, SleepAnswer::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}