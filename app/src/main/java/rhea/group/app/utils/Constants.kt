package rhea.group.app.utils

import rhea.group.app.BuildConfig
import rhea.group.app.models.BreathworkOption
import rhea.group.app.models.Exercise
import rhea.group.app.models.SleepQuestion

object Constants {
    const val EXO_PLAYER_USER_AGENT = BuildConfig.APPLICATION_ID
    const val EXO_PLAYER_VIDEO_CACHE_DURATION = 10 * 1024 * 1024
}

var exercises: List<Exercise> = emptyList()
var sleepQuestions: List<SleepQuestion> = emptyList()
var breathworkOptionParam: BreathworkOption = BreathworkOption()