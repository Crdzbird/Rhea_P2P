package rhea.group.app.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Plan(
    @SerializedName("created")
    val created: Int = 0, // 1635202099
    @SerializedName("current_stage")
    val currentStage: String = "", // stage_42044273721362
    @SerializedName("exercise_activity")
    val exerciseActivity: Any? = Any(), // null
    @SerializedName("id")
    val id: String = "", // plan_42044508602386
    @SerializedName("injury_date")
    val injuryDate: String = "", // 2021-08-04T01:53:06Z
    @SerializedName("last_session_change_date")
    val lastSessionChangeDate: String = "", // 2021-08-04T01:53:06Z
    @SerializedName("last_stage_change_date")
    val lastStageChangeDate: String = "", // 2021-08-04T01:53:06Z
    @SerializedName("next_stages")
    val nextStages: List<String> = listOf(),
    @SerializedName("plan_category")
    val planCategory: String = "", // traditional_plan_1
    @SerializedName("previous_stages")
    val previousStages: Any? = Any(), // null
    @SerializedName("start_date")
    val startDate: String = "", // 2021-08-04T01:53:06Z
    @SerializedName("user_id")
    val userId: String = "" // user_41894486736914
) {
    companion object {
        fun fromJson(json: String): Plan = gson.fromJson(json, Plan::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}