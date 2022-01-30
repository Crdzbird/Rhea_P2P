package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Reason(
    @SerializedName("reason")
    val reason: ReasonType = ReasonType.reason_other // reason_too_difficult
) {
    companion object {
        fun fromJson(json: String): Reason = gson.fromJson(json, Reason::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}