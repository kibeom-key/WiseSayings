package kr.non.wsayings.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.widget.RemoteViews
import dagger.hilt.android.AndroidEntryPoint
import kr.non.wsayings.AlarmSetter
import kr.non.wsayings.BuildConfig
import kr.non.wsayings.R
import kr.non.wsayings.db.WiseSayingRepository
import kr.non.wsayings.ui.activity.MainActivity
import kr.non.wsayings.utils.BitmapUtil
import javax.inject.Inject

@AndroidEntryPoint
class WiseSayingAppWidget : AppWidgetProvider() {

    @Inject
    lateinit var wiseSayingRepository: WiseSayingRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, wiseSayingRepository)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        AlarmSetter(context).set()
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    wiseSayingRepository: WiseSayingRepository
) {

    val item = wiseSayingRepository.getWiseSaying()

    val views = RemoteViews(
        context.packageName,
        R.layout.wise_saying_app_widget
    ).apply{

        // 위젯은 커스텀 폰트가 적용되지 않는다.
        // 명언은 35자 이상은 15DP 로 설정한다. (최대 50자를 넘기지 않도록 구성하자.)

        val saying = item.wiseSaying
        var fontSize = if (saying.length > 35) {
            15f
        } else {
            17f
        }

        setTextViewText(R.id.quotes_title, saying)
        setTextViewTextSize(R.id.quotes_title, TypedValue.COMPLEX_UNIT_DIP, fontSize)
        setTextViewText(R.id.quotes_writer, item.author)

        val idx = (item.id % BuildConfig.TotalNumberOfBackgrouns) + 1
        val resourceName = "bg_$idx"
        val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        var bitmap = BitmapUtil.getBitmap4Widget(context, resourceId)
        bitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 60);

        setImageViewBitmap(R.id.img_background, bitmap)
        setOnClickPendingIntent(R.id.quotes_container, getLaunchPendingIntent(context))
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getLaunchPendingIntent(context: Context) : PendingIntent {

    return PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java), 0)
}