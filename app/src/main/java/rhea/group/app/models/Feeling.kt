package rhea.group.app.models


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Feeling(
    @SerializedName("feeling")
    val feeling: FeelingsType = FeelingsType.positive // positive
) {
    companion object {
        fun fromJson(json: String): Feeling = gson.fromJson(json, Feeling::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}