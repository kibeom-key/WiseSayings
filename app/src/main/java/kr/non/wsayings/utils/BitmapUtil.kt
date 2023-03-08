package kr.non.wsayings.utils

import android.content.Context
import android.graphics.*
import android.util.TypedValue


class BitmapUtil {

    // 참고 : https://black-jin0427.tistory.com/96

    companion object{

        // 홈 스크린 위젯 사이즈
        // private const val widgetSize : Int = 190

        // 위젯 비트맵
        fun getBitmap4Widget(context: Context, resourceId : Int) : Bitmap{

            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

            // 중심좌표
            val x = bitmap.width/2
            val y = bitmap.height/2

            // val size = dpToPx(context, widgetSize).toInt()
            val size = bitmap.height/2

            return Bitmap.createBitmap(bitmap, x-(size/2), y-(size/2), size, size)
        }

        // 위젯 (와이드) 비트맵
        fun getBitmap4WideWidget(context: Context, resourceId : Int) : Bitmap{

            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

            // 중심좌표
            val x = bitmap.width/2
            val y = bitmap.height/2

            // val size = dpToPx(context, widgetSize).toInt()
            val size = bitmap.height/2

            return Bitmap.createBitmap(bitmap, x-(size), y-(size/2), size * 2, size)
        }

        // '설정'화면 비트맵
        fun getBitmap4Screen(context: Context, resourceId: Int) : Bitmap{

            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
            val deviceWidth = context.resources.displayMetrics.widthPixels
            // val deviceHeight = context.resources.displayMetrics.heightPixels
            val scaleHeight = deviceWidth * bitmap.height / bitmap.width

            // Log.d("@!@", "deviceWidth : $deviceWidth")

            return Bitmap.createScaledBitmap(bitmap, deviceWidth, scaleHeight, true)
        }

        // 라운드 비트맵
        fun getRoundedCornerBitmap(bitmap: Bitmap, roundPixelSize: Int) : Bitmap{

            val output = Bitmap.createBitmap(
                bitmap.width,
                bitmap.height,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(output)
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = roundPixelSize.toFloat()

            paint.isAntiAlias = true
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)

            return output
        }

        private fun dpToPx(context: Context, dp: Int): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
        }
    }
}