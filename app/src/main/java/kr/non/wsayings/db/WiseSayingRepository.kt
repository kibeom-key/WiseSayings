package kr.non.wsayings.db

import kr.non.wsayings.UserPreferences


class WiseSayingRepository(
    private val db : AppDatabase,
    private val pref : UserPreferences) {

    fun getWiseSaying() : WiseSaying {

        val key = UserPreferences.KEY_LONG__WISE_SAYING_INDEX
        var item = db.wiseSayingDao().getNext(pref.getLong(key))

        // Wise sayings are sold out.
        if(item == null){
            pref.putLong(key, -1)
            item = db.wiseSayingDao().getNext(-1)!!
        }

        return item
    }
}