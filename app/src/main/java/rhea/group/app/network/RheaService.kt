package rhea.group.app.network

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rhea.group.app.models.*

interface RheaService {
    @POST("public/authentication/authenticate")
    suspend fun authenticate(@Body authCredentials: AuthCredentials): ApiResponse<AuthResponse>

    @GET("public/profile")
    suspend fun profile(): ApiResponse<Profile>

    @POST("public/authentication/refresh")
    suspend fun refresh(@Body authResponse: AuthResponse): ApiResponse<AuthResponse>

    @GET("public/plan")
    suspend fun plan(): ApiResponse<Plan>

    @GET("public/stage/{stage}")
    suspend fun stage(@Path("stage") stage: String): ApiResponse<Stage>

    @GET("public/session/{session}")
    suspend fun session(@Path("session") session: String): ApiResponse<Session>

    @POST("public/session/{session}/cancel")
    suspend fun cancelSession(
        @Path("session") session: String,
        @Body reason: Reason
    ): ApiResponse<Unit>

    @POST("public/session/{session}/complete")
    suspend fun completeWorkout(
        @Path("session") session: String,
        @Body completeWorkout: Any // This will be used as a generic api function with sleep hygiene and video completion.
    ): ApiResponse<Unit>
}