package rhea.group.app.navigator.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rhea.group.app.navigator.ComposeNavigator
import rhea.group.app.navigator.RheaComposeNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun provideComposeNavigator(rheaComposeNavigator: RheaComposeNavigator): ComposeNavigator
}
