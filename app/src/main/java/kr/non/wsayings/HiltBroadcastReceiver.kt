package kr.non.wsayings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper

abstract class HiltBroadcastReceiver : BroadcastReceiver() {

    // https://github.com/google/dagger/issues/1918
    // https://stackoverflow.com/questions/62335727/hilt-injection-not-working-with-broadcastreceiver

    @CallSuper
    override fun onReceive(context: Context?, intent: Intent?) {}
}