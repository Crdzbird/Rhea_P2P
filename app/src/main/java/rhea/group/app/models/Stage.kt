package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Stage(
    @SerializedName("additional_breathwork_session")
    val additionalBreathworkSession: String? = "", // session_2688751650209799
    @SerializedName("average_duration")
    val averageDuration: String = "", // 3-5 Days
    @SerializedName("closing_motivational_text")
    val closingMotivationalText: String? = "", // You've just completed Care! In the next stage, we'll step up the level of physical exercise.
    @SerializedName("completed_additional_breathwork_sessions")
    val completedAdditionalBreathworkSessions: List<String>? = emptyList(), // null
    @SerializedName("created")
    val created: Int = 0, // 1641841754
    @SerializedName("current_session")
    val currentSession: String = "", // session_2688751683764231
    @SerializedName("equipment")
    val equipment: List<String> = listOf(),
    @SerializedName("header_image_url")
    val headerImageUrl: String = "", // https://rhea-public.s3.ca-central-1.amazonaws.com/Header.jpg
    @SerializedName("id")
    val id: String = "", // stage_2688751734095879
    @SerializedName("motivational_text")
    val motivationalText: String? = "", // We hope you're enjoying these exercises. These movements have been tailored just for you.
    @SerializedName("name")
    val name: String = "", // Aerobic Resistance Training
    @SerializedName("next_sessions")
    val nextSessions: List<String>? = listOf(),
    @SerializedName("previous_sessions")
    val previousSessions: List<String>? = listOf(),
    @SerializedName("stage_category")
    val stageCategory: String = "", // aerobic_1
    @SerializedName("user_id")
    val userId: String = "" // user_2688728732532743
) {
    companion object {
        fun fromJson(json: String): Stage = gson.fromJson(json, Stage::class.java)
    }

    fun toJson(): String = gson.toJson(this)

    fun combine(): List<StageSession> {
        val stageSessions: MutableList<StageSession> = mutableListOf()
        previousSessions?.let {
            it.map { ps ->
                stageSessions.add(
                    StageSession(
                        isCompleted = true,
                        additionalBreathworkSession = additionalBreathworkSession,
                        isPending = false,
                        isActive = false,
                        completedAdditionalBreathworkSessions = "",
                        completionDate = "",
                        updateDate = created,
                        recommendedTime = averageDuration,
                        category = Category.Unknown,
                        session = ps
                    )
                )
            }
        }
        stageSessions.add(
            StageSession(
                isCompleted = false,
                additionalBreathworkSession = additionalBreathworkSession,
                isPending = true,
                isActive = true,
                completionDate = "",
                completedAdditionalBreathworkSessions = "",
                updateDate = created,
                recommendedTime = averageDuration,
                category = Category.Unknown,
                session = currentSession
            )
        )
        nextSessions?.let {
            it.map { ns ->
                stageSessions.add(
                    StageSession(
                        isCompleted = false,
                        additionalBreathworkSession = additionalBreathworkSession,
                        isPending = true,
                        isActive = false,
                        completionDate = "",
                        completedAdditionalBreathworkSessions = "",
                        updateDate = created,
                        recommendedTime = averageDuration,
                        category = Category.Unknown,
                        session = ns
                    )
                )
            }
        }
        return stageSessions.toList()
    }

    fun progress(stagesSessionList: List<StageSession>): Int {
        val total = stagesSessionList.size.toFloat()
        val done = stagesSessionList.filter { stage -> stage.isCompleted }.size.toFloat()
        val progress: Float = done / total
        return (progress * 100f).toInt()
    }
}
