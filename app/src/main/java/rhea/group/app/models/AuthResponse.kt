package rhea.group.app.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

private val gson = Gson()

data class AuthResponse(
    @SerializedName("auth_token")
    val auth_token: String = "", // eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImphY2suY2hvcmxleUBtZS5jb20iLCJleHAiOjE2MzYxNDQ4MDMsInVzZXJfaWQiOiJ1c2VyXzQxODk0NDg2NzM2OTE0In0.J0HJiC3GwUqSagBWGww9rqG_kgBg9o5VDVK5O-qyOQBN-lz4GKRJJXCLnXTSy__Ejj_67gxV5XuxJI3yf9lnEg
    @SerializedName("refresh_token")
    val refresh_token: String = "" // eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImphY2suY2hvcmxleUBtZS5jb20iLCJleHAiOjE2Mzg2NTA0MDMsInVzZXJfaWQiOiJ1c2VyXzQxODk0NDg2NzM2OTE0In0.3HdyDDZD-rdyqnG89l1TRu0x9w9WzUW233oy_0Q0vejCCy8fyemuUSIaPGwpJ7ED0pMZmXobH0jgVImiEHhuDg
) {
    companion object {
        fun fromJson(json: String): AuthResponse = gson.fromJson(json, AuthResponse::class.java)
    }

    fun toJson(): String = gson.toJson(this)
}