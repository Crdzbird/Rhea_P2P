package rhea.group.app.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.utils.getLastChunk
import rhea.group.app.utils.removeLastChunk
import rhea.group.app.utils.toStringURL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RheaInterceptor @Inject constructor(private val preferences: PrefsModeImpl) : Interceptor {
    companion object RheaApiConstants {
        const val AUTHENTICATION = "/public/authentication/authenticate"
        const val FETCH_PROFILE = "/public/profile"
        const val REFRESH = "/public/authentication/refresh"
        const val PLAN = "/public/plan"
        const val STAGE = "/public/stage"
        const val SUB_STAGE = "public/stage"
        const val SESSION = "/public/session"
        const val SUB_SESSION = "public/session"
        const val COMPLETE_SUB_SESSION = "complete"
        const val ENDING_SUB_SESSION = "cancel"
    }

    @Synchronized
    override fun intercept(chain: Interceptor.Chain): Response {
        val authResponse = runBlocking { preferences.authResponse.first() }
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        when (request.url.encodedPath) {
            AUTHENTICATION -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            FETCH_PROFILE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
            }
            REFRESH -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            PLAN -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
            }
            STAGE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
            }
            SESSION -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
            }
            else -> {
                when (request.url.encodedPathSegments.toMutableList().removeLastChunk()) {
                    SUB_STAGE -> {
                        requestBuilder
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
                    }
                    SUB_SESSION -> {
                        requestBuilder
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
                    }
                    COMPLETE_SUB_SESSION -> {
                        requestBuilder
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
                    }
                    ENDING_SUB_SESSION -> {
                        requestBuilder
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer ${authResponse?.auth_token ?: ""}")
                    }
                    else -> {
                        when (request.url.encodedPathSegments.toMutableList().toStringURL()
                            .getLastChunk()) {
                            ENDING_SUB_SESSION -> {
                                requestBuilder
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader(
                                        "Authorization",
                                        "Bearer ${authResponse?.auth_token ?: ""}"
                                    )
                            }
                            COMPLETE_SUB_SESSION -> {
                                requestBuilder
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader(
                                        "Authorization",
                                        "Bearer ${authResponse?.auth_token ?: ""}"
                                    )
                            }
                            else -> {
                                requestBuilder
                                    .addHeader("Content-Type", "application/json")
                            }
                        }
                    }
                }
            }
        }
        request = requestBuilder.build()
        return chain.proceed(request)
    }
}