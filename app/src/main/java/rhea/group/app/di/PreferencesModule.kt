package rhea.group.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rhea.group.app.preferences.AppDataStore
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): PrefsModeImpl {
        return AppDataStore(context)
    }
}