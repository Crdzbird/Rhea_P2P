package rhea.group.app.di

import android.content.Context
import coil.util.CoilUtils
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rhea.group.app.BuildConfig
import rhea.group.app.network.RheaInterceptor
import rhea.group.app.network.RheaService
import rhea.group.app.preferences.PrefsModeImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        prefs: PrefsModeImpl
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(RheaInterceptor(prefs))
            .addInterceptor(loggingInterceptor)
            .cache(CoilUtils.createDefaultCache(context))
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://apiv2.getrhea.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRheaService(retrofit: Retrofit): RheaService {
        return retrofit.create(RheaService::class.java)
    }
}