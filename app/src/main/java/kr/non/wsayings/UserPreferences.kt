package kr.non.wsayings

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class UserPreferences(context : Context) {

    private val sharedPreferences: SharedPreferences by lazy {

        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        EncryptedSharedPreferences.create(
            context.packageName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    fun getString(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getInt(key: String) : Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getLong(key: String) : Long {
        return sharedPreferences.getLong(key, -1)
    }

    fun getLong(key: String, default : Long) : Long {
        return sharedPreferences.getLong(key, default)
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean) : Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }


    companion object{
        const val KEY_LONG__WISE_SAYING_INDEX = "wise_saying_index"
        const val KEY_LONG__USER_SAYING_UPLOAD_TIME = "user_saying_upload_time"
        const val KEY_BOOL__APP_IS_FIRST_RUN = "is_first_run"
    }

}