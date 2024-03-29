package huuloc.uit.edu.truyenqq.data

import android.content.Context

const val USER_ID = "id"
const val USER_NAME = "username"
const val EMAIL = "email"
const val AVATAR = "avatar"
const val SEX = "gender"
const val FIRST_NAME = "first_name"
const val LAST_NAME = "last_name"
const val BIRTHDAY_STRING = "birthday_string"
const val PHONE = "phone"

class MysharedPreferences(context: Context) {
    private val APP_NAME = "truyenqq"
    val getShare = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor = getShare.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {

        val editor = getShare.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun removeAll() {
        val editor = getShare.edit()
        editor.clear()
        editor.apply()
    }

    fun saveData(data: DataLogin) {
        val put = getShare.edit()
        put.putString(USER_ID, data.id)
        put.putString(USER_NAME, data.username)
        put.putString(EMAIL, data.email)
        put.putString(AVATAR, data.avatar)
        put.putString(SEX, data.sex)
        put.putString(FIRST_NAME, data.first_name)
        put.putString(LAST_NAME, data.last_name)
        put.putString(BIRTHDAY_STRING, data.birthday_string)
        put.putString(PHONE, data.phone)
        put.apply()
    }

    fun loadData(): DataLogin? {
        val data = DataLogin(null, null, null, null, null, null, null, null, null,"")
        data.id = getShare.getString(USER_ID, null)
        if (data.id == null)
            return null
        data.username = getShare.getString(USER_NAME, null)
        data.email = getShare.getString(EMAIL, null)
        data.avatar = getShare.getString(AVATAR, null)
        data.sex = getShare.getString(SEX, null)
        data.first_name = getShare.getString(FIRST_NAME, null)
        data.last_name = getShare.getString(LAST_NAME, null)
        data.birthday_string = getShare.getString(BIRTHDAY_STRING, null)
        data.phone = getShare.getString(PHONE, null)
        return data
    }
}