package kr.non.wsayings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmSetter(private val context: Context) {

    // 3끼 : 09:00, 13:00, 17:00
    fun set(){

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                context, ALARM_REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, next())
            set(Calendar.MINUTE, 0)
        }

        // 알람이 과거 시간으로 설정 될 때 바로 알람이 들어오는 현상 방지
        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }

    fun isSet() : Boolean{
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                context, ALARM_REQUEST_CODE, intent,
                PendingIntent.FLAG_NO_CREATE
        )

        return pendingIntent != null
    }


    private fun next() : Int {

        val alarmTimes = arrayOf(9, 13, 17)
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        for (time in alarmTimes){
            if(currentTime < time){
                return time
            }
        }

        return alarmTimes[0]
    }

    companion object{
        const val ALARM_REQUEST_CODE = 1024;
    }
}


