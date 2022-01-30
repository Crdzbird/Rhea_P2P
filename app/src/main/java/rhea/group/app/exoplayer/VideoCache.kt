package rhea.group.app.exoplayer

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

object VideoCache {
    private var sDownloadCache: SimpleCache? = null
    private const val maxCacheSize: Long = 100 * 1024 * 1024
    private lateinit var databaseProvider: DatabaseProvider

    fun getInstance(context: Context): SimpleCache {
        val evictor = LeastRecentlyUsedCacheEvictor(maxCacheSize)
        databaseProvider = StandaloneDatabaseProvider(context)
        if (sDownloadCache == null) sDownloadCache =
            SimpleCache(File(context.cacheDir, "media"), evictor, databaseProvider)
        return sDownloadCache as SimpleCache
    }
}