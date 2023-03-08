package kr.non.wsayings

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kr.non.wsayings.db.AppDatabase
import kr.non.wsayings.widget.WiseSayingAppWidget
import kr.non.wsayings.widget.WiseSayingWideAppWidget
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var appDatabase : AppDatabase

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onReceive(context: Context?, intent: Intent?) {

        super.onReceive(context, intent)

        // 다음 명언 획득
        val key = UserPreferences.KEY_LONG__WISE_SAYING_INDEX
        var newIdx = -1L
        val item = appDatabase.wiseSayingDao().getNext(userPreferences.getLong(key))
        if(item != null){
            newIdx = item.id
        }
        userPreferences.putLong(key, newIdx)

        // 알람 재설정
        context?.let {
            AlarmSetter(it).set()
        }

        // 위젯 업데이트
        context?.let {

            // 3x2
            updateWidget(it, WiseSayingAppWidget::class.java)
            // 5x2
            updateWidget(it, WiseSayingWideAppWidget::class.java)

            /*
            val widgetName = ComponentName(context.packageName, WiseSayingAppWidget::class.java.name)
            val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(widgetName)
            val updateIntent = Intent(context, WiseSayingAppWidget::class.java)
            updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(updateIntent)
            */
        }
    }

    private fun updateWidget(context: Context, cls: Class<*>){

        val widgetName = ComponentName(context.packageName, cls.name)
        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(widgetName)
        val updateIntent = Intent(context, cls)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(updateIntent)
    }


}