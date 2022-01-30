package rhea.group.app.timer

import android.os.CountDownTimer

class RheaTimer(
    durationInMilliseconds: Long,
    private val onCountdownTick: (durationLeftInMilliseconds: Long) -> Unit,
    private val onCountdownFinished: () -> Unit,
    tickInterval: Long = 100L
) : CountDownTimer(durationInMilliseconds, tickInterval) {

    override fun onTick(millisUntilFinished: Long) {
        onCountdownTick(millisUntilFinished)
    }

    override fun onFinish() {
        onCountdownFinished()
    }
}