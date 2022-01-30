package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class VideoSection(
    @SerializedName("detail")
    val detail: String = "", // 1 minute each interval x 3
    @SerializedName("exercises")
    val exercises: List<Exercise> = listOf(),
    @SerializedName("name")
    val name: String = "" // Warmup
) {
    companion object {
        fun fromJson(json: String): Session = gson.fromJson(json, Session::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}