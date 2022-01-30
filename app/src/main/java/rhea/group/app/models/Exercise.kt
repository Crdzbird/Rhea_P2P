package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

enum class ExerciseCategoryType {
    Rest,
    Video
}

data class Exercise(
    @SerializedName("duration")
    val duration: Int = 0, // 120
    @SerializedName("name")
    val name: String = "", // Single Leg Kettlebell
    @SerializedName("preview_description")
    val previewDescription: List<String> = listOf(),
    @SerializedName("preview_image_url")
    val previewImageUrl: String = "", // https://rhea-public.s3.ca-central-1.amazonaws.com/Header.jpg
    @SerializedName("preview_url")
    val previewUrl: String = "", // https://rhea-video-content.s3.ca-central-1.amazonaws.com/BinPush/BinPush.m3u8
    @SerializedName("type")
    val type: String = "", // video
    @SerializedName("video_url")
    val videoUrl: String = "" // https://rhea-video-content.s3.ca-central-1.amazonaws.com/BinPush/BinPush.m3u8
) {
    companion object {
        fun fromJson(json: String): Session = gson.fromJson(json, Session::class.java)
    }

    fun toJson(): String = gson.toJson(this)

    fun toEnumExerciseCategoryType(): ExerciseCategoryType {
        return when (type) {
            ExerciseCategoryType.Rest.name.lowercase() -> ExerciseCategoryType.Rest
            ExerciseCategoryType.Video.name.lowercase() -> ExerciseCategoryType.Video
            else -> ExerciseCategoryType.Video
        }
    }
}