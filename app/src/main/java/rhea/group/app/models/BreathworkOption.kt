package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class BreathworkOption(
    @SerializedName("background_video_url")
    val backgroundVideoUrl: String = "", // https://rhea-video-content.s3.ca-central-1.amazonaws.com/Breathing/Breathing.m3u8
    @SerializedName("breathwork_type")
    val breathworkType: String = "", // box_breathing
    @SerializedName("description")
    val description: String = "",
    @SerializedName("exhale")
    val exhale: Int = 0, // 4
    @SerializedName("exhale_hold")
    val exhaleHold: Int = 0, // 4
    @SerializedName("inhale")
    val inhale: Int = 0, // 4
    @SerializedName("inhale_hold")
    val inhaleHold: Int = 0, // 4
    @SerializedName("instructions")
    val instructions: List<String>? = emptyList(), // null
    @SerializedName("max_mins")
    val maxMins: Int = 0, // 0
    @SerializedName("name")
    val name: String = "" // Box Breathing
) {
    companion object {
        fun fromJson(json: String): BreathworkOption =
            gson.fromJson(json, BreathworkOption::class.java)
    }

    val breathLimits: List<Int>
        get() = listOf(inhale, inhaleHold, exhale, exhaleHold)

    val segmentBreathing: List<Map<String, Any>>
        get() {
            return listOf(
                mapOf(
                    "name" to "inhale",
                    "duration" to inhale
                ),
                mapOf(
                    "name" to "hold",
                    "duration" to inhaleHold
                ),
                mapOf(
                    "name" to "exhale",
                    "duration" to exhale
                ),
                mapOf(
                    "name" to "hold",
                    "duration" to exhaleHold
                )
            )
        }

    fun toJson(): String = gson.toJson(this)

}