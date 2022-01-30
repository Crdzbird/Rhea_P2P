package rhea.group.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import rhea.group.app.network.RheaService
import rhea.group.app.preferences.PrefsModeImpl
import rhea.group.app.ui.screens.authentication.repository.EmailAuthRepository
import rhea.group.app.ui.screens.authentication.repository.ProfileRepository
import rhea.group.app.ui.screens.ending_workout_screen.repository.EndingWorkoutRepository
import rhea.group.app.ui.screens.sleep_questionnaire.repository.SleepQuestionnaireRepository
import rhea.group.app.ui.screens.stage.repository.PlanRepository
import rhea.group.app.ui.screens.stage.repository.SessionRepository
import rhea.group.app.ui.screens.stage.repository.StageRepository
import rhea.group.app.ui.screens.symptoms.repository.BreathworkRepository
import rhea.group.app.ui.screens.symptoms.repository.SymptomsRepository
import rhea.group.app.worker.RefreshTokenRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideEmailAuthRepository(
        rheaService: RheaService
    ): EmailAuthRepository {
        return EmailAuthRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileRepository(
        rheaService: RheaService
    ): ProfileRepository {
        return ProfileRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideEndingWorkoutRepository(
        rheaService: RheaService
    ): EndingWorkoutRepository {
        return EndingWorkoutRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideCompleteWorkoutRepository(
        rheaService: RheaService
    ): SymptomsRepository {
        return SymptomsRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideBreathworkRepository(
        rheaService: RheaService
    ): BreathworkRepository {
        return BreathworkRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun providePlanRepository(
        rheaService: RheaService
    ): PlanRepository {
        return PlanRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideStageRepository(
        rheaService: RheaService
    ): StageRepository {
        return StageRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideSessionRepository(
        rheaService: RheaService
    ): SessionRepository {
        return SessionRepository(rheaService = rheaService)
    }

    @Provides
    @ViewModelScoped
    fun provideRefreshTokenRepository(
        rheaService: RheaService,
        preferences: PrefsModeImpl
    ): RefreshTokenRepository {
        return RefreshTokenRepository(rheaService = rheaService, preferences = preferences)
    }

    @Provides
    @ViewModelScoped
    fun provideSleepQuestionnaireRepository(
        rheaService: RheaService
    ): SleepQuestionnaireRepository {
        return SleepQuestionnaireRepository(rheaService = rheaService)
    }
}