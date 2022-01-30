package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class SleepQuestion(
    @SerializedName("id")
    val id: String = "", // sleep_question_1
    @SerializedName("title")
    val title: String = "", // Did you eat dinner at least 3-4 hours prior to bed?
    @SerializedName("subtitle")
    val subtitle: String? = "", // e.g., black out curtains
) {
    companion object {
        fun fromJson(json: String): SleepQuestion = gson.fromJson(json, SleepQuestion::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}