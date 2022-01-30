package rhea.group.app.di

import android.content.Context
import com.google.android.exoplayer2.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rhea.group.app.exoplayer.LivePlayer
import rhea.group.app.exoplayer.LivePlayerInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {
    @Singleton
    @Provides
    fun provideLivePlayerInterface(): Player.Listener {
        return LivePlayerInterface()
    }

    @Singleton
    @Provides
    fun provideLivePlayer(@ApplicationContext context: Context): LivePlayer {
        return LivePlayer(context) {}
    }
}