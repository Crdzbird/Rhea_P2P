package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Session(
    @SerializedName("breathwork_options")
    val breathworkOptions: List<BreathworkOption>? = listOf(),
    @SerializedName("brief")
    val brief: String = "", // Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean eu leo quam.
    @SerializedName("completed_time")
    val completedTime: String = "", // 2021-10-25T22:49:48Z
    @SerializedName("created")
    val created: Int = 0, // 1635202099
    @SerializedName("description")
    val description: String = "", // Donec id elit non mi porta gravida at eget metus. Donec sed odio dui. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec id elit non mi porta gravida at eget metus. Donec sed odio dui.
    @SerializedName("duration")
    val duration: Int = 0, // 1590
    @SerializedName("equipment")
    val equipment: List<String>? = listOf(),
    @SerializedName("feeling")
    val feeling: String = "", // positive
    @SerializedName("id")
    val id: String = "", // session_42044240166930
    @SerializedName("motivational_text")
    val motivationalText: String = "", // We hope you're enjoying these exercises. These movements have been tailored just for you.
    @SerializedName("name")
    val name: String = "", // Circuit Training
    @SerializedName("no")
    val no: Int = 0, // 1
    @SerializedName("session_type")
    val sessionType: String = "", // base
    @SerializedName("sleep_questions")
    val sleepQuestions: List<SleepQuestion>? = listOf(), // null
    @SerializedName("target_heart_rate")
    val targetHeartRate: Int = 0, // 125
    @SerializedName("total_duration")
    val totalDuration: Int = 0, // -38
    @SerializedName("user_id")
    val userId: String = "", // user_41894486736914
    @SerializedName("video_sections")
    val videoSections: List<VideoSection>? = listOf()
) {
    companion object {
        fun fromJson(json: String): Session = gson.fromJson(json, Session::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}