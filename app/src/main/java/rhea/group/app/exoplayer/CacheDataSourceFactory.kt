package rhea.group.app.exoplayer

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.MimeTypes


class CacheDataSourceFactory(private val context: Context) :
    DataSource.Factory {

    override fun createDataSource(): DataSource {
        val dataSourceFactory = DefaultDataSource.Factory(context)
        val simpleCache: SimpleCache by lazy {
            VideoCache.getInstance(context)
        }
        return CacheDataSource(simpleCache, dataSourceFactory.createDataSource())
    }

    fun createMediaSource(source: String): MediaSource {
        return createMediaSourceFactory(source, this)
            .createMediaSource(
                MediaItem.Builder()
                    .setMimeType(MimeTypes.APPLICATION_M3U8)
                    .setUri(source)
                    .build()
            )
    }

    private fun createMediaSourceFactory(
        source: String,
        dataSourceFactory: DataSource.Factory
    ): MediaSourceFactory {
        val extension = source.substringAfterLast('.', "")
        return if (extension == "m3u8" || extension == "m3u") {
            HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(true)
        } else {
            ProgressiveMediaSource.Factory(dataSourceFactory)
        }
    }
}