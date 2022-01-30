package rhea.group.app.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class Profile(
    @SerializedName("email")
    val email: String = "", // jack.chorley@me.com
    @SerializedName("first_name")
    val firstName: String = "", // Jack
    @SerializedName("id")
    val id: String = "", // user_41894486736914
    @SerializedName("last_name")
    val lastName: String = "", // Chorley
    @SerializedName("trial_status")
    val trialStatus: String = "" // paid
) {
    companion object {
        fun fromJson(json: String): Profile = gson.fromJson(json, Profile::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}
