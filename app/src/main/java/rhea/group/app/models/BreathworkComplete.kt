package rhea.group.app.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


private val gson = Gson()

data class BreathworkComplete(
    @SerializedName("feeling")
    val feeling: FeelingsType = FeelingsType.positive, // positive
    @SerializedName("total_duration") // 120
    val total_duration: Int = 0,
    @SerializedName("breathwork_type") // box_breathing
    val breathwork_type: String = ""
) {
    companion object {
        fun fromJson(json: String): BreathworkComplete =
            gson.fromJson(json, BreathworkComplete::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}