package kr.non.wsayings.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class CaptureUtil {

    companion object{

        // https://medium.com/@hiteshkrsahu/a-complete-guide-for-taking-screenshot-in-android-28-bcb9a19a2b6e

        /**
         * Copy View to Canvas and return bitMap
         * Won't work on Surface View
         */
        fun getBitmapFromView(view: View): Bitmap? {
            var bitmap =
                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

    }
}