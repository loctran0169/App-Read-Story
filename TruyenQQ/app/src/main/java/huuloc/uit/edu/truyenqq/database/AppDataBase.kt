package huuloc.uit.edu.truyenqq.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageChap::class], version = 3)
abstract class AppDataBase : RoomDatabase() {

    abstract fun ImageChapDAO(): ImageChapDAO

    companion object {
        private var sInstance: AppDataBase? = null

        fun get(context: Context): AppDataBase? {
            if (sInstance == null) {
                synchronized(AppDataBase::class) {
                    sInstance = Room.databaseBuilder(context, AppDataBase::class.java, "image_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance
        }
    }
}