package rhea.group.app.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rhea.group.app.models.*
import javax.inject.Singleton

abstract class PrefsDataStore(context: Context, fileName: String) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(fileName)
    val mDataStore: DataStore<Preferences> = context.dataStore
}

class AppDataStore(context: Context) : PrefsDataStore(context = context, PREF_FILE), PrefsModeImpl {
    companion object {
        const val PREF_FILE = "rhea_prefs"
        private val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
        private val AUTH_RESPONSE = stringPreferencesKey("authResponse")
        val PROFILE = stringPreferencesKey("profile")
        private val PLAN = stringPreferencesKey("plan")
        private val STAGE = stringPreferencesKey("stage")
        private val SESSIONS = stringSetPreferencesKey("sessions")
        private val BPM = intPreferencesKey("bpm")
        private val WEARABLE_ID = stringPreferencesKey("wearableId")
    }

    override suspend fun session(sessionId: String): Flow<Session?> {
        return mDataStore.data.map { prefs ->
            if (prefs[SESSIONS].isNullOrEmpty()) null else prefs[SESSIONS]!!.map {
                Session.fromJson(
                    it
                )
            }.find { it.id == sessionId }
        }
    }

    override val sessions: Flow<List<Session>?>
        get() = mDataStore.data.map { prefs ->
            if (prefs[SESSIONS].isNullOrEmpty()) listOf() else prefs[SESSIONS]!!.map {
                Session.fromJson(
                    it
                )
            }.toList()
        }

    override val isLoggedIn: Flow<Boolean>
        get() = mDataStore.data.map { prefs ->
            prefs[IS_LOGGED_IN] ?: false
        }

    override val wearableId: Flow<String?>
        get() = mDataStore.data.map { prefs ->
            prefs[WEARABLE_ID]
        }

    override val authResponse: Flow<AuthResponse?>
        get() = mDataStore.data.map { prefs ->
            if (prefs[AUTH_RESPONSE].isNullOrEmpty()) null else AuthResponse.fromJson(prefs[AUTH_RESPONSE]!!)
        }

    override val profile: Flow<Profile?>
        get() = mDataStore.data.map { prefs ->
            if (prefs[PROFILE].isNullOrEmpty()) null else Profile.fromJson(prefs[PROFILE]!!)
        }

    override val bpm: Flow<Int>
        get() = mDataStore.data.map { prefs ->
            prefs[BPM] ?: 0
        }

    override val plan: Flow<Plan?>
        get() = mDataStore.data.map { prefs ->
            if (prefs[PLAN].isNullOrEmpty()) null else Plan.fromJson(prefs[PLAN]!!)
        }

    override val stage: Flow<Stage?>
        get() = mDataStore.data.map { prefs ->
            if (prefs[STAGE].isNullOrEmpty()) null else Stage.fromJson(prefs[STAGE]!!)
        }

    override suspend fun updateLogStatus(log: Boolean) {
        mDataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = log
        }
    }

    override suspend fun updateAuthResponse(s: AuthResponse) {
        mDataStore.edit { prefs ->
            prefs[AUTH_RESPONSE] = s.toJson()
        }
    }

    override suspend fun updateProfile(profile: Profile) {
        mDataStore.edit { prefs ->
            prefs[PROFILE] = profile.toJson()
        }
    }

    override suspend fun updateWearableId(id: String) {
        mDataStore.edit { prefs ->
            prefs[WEARABLE_ID] = id
        }
    }

    override suspend fun updateBpm(bpm: Int) {
        mDataStore.edit { prefs ->
            prefs[BPM] = bpm
        }
    }

    override suspend fun updatePlan(plan: Plan) {
        mDataStore.edit { prefs ->
            prefs[PLAN] = plan.toJson()
        }
    }

    override suspend fun updateStage(stage: Stage) {
        mDataStore.edit { prefs ->
            prefs[STAGE] = stage.toJson()
        }
    }

    override suspend fun updateSession(session: Session) {
        mDataStore.edit { prefs ->
            if (prefs[SESSIONS].isNullOrEmpty()) {
                prefs[SESSIONS] = setOf(session.toJson())
            } else {
                val list = prefs[SESSIONS]!!.map {
                    Session.fromJson(it)
                }
                val pos = list.indexOfFirst { it.id == session.id }
                if (pos != -1) {
                    val mutableList = list.toMutableList()
                    mutableList[pos] = session
                    prefs[SESSIONS] = mutableList.map { it.toJson() }.toSet()
                } else {
                    prefs[SESSIONS] = prefs[SESSIONS]!!.plus(session.toJson())
                }
            }
        }
    }

    override suspend fun updateSessions(sessions: List<Session>) {
        mDataStore.edit { prefs ->
            prefs[SESSIONS] = sessions.map { session -> session.toJson() }.toSet()
        }
    }

    override suspend fun clearSession() {
        mDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}

@Singleton
interface PrefsModeImpl {
    val isLoggedIn: Flow<Boolean>
    val authResponse: Flow<AuthResponse?>
    val profile: Flow<Profile?>
    val bpm: Flow<Int>
    val plan: Flow<Plan?>
    val stage: Flow<Stage?>
    val sessions: Flow<List<Session>?>
    val wearableId: Flow<String?>
    suspend fun session(sessionId: String): Flow<Session?>
    suspend fun updateLogStatus(log: Boolean)
    suspend fun updateWearableId(id: String)
    suspend fun updateAuthResponse(s: AuthResponse)
    suspend fun updateProfile(profile: Profile)
    suspend fun updateBpm(bpm: Int)
    suspend fun updatePlan(plan: Plan)
    suspend fun updateStage(stage: Stage)
    suspend fun updateSession(session: Session)
    suspend fun updateSessions(sessions: List<Session>)
    suspend fun clearSession()
}