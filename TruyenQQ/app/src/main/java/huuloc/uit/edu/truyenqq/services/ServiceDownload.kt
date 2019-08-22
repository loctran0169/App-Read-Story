package huuloc.uit.edu.truyenqq.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.main.MainActivity
import huuloc.uit.edu.truyenqq.database.ImageChap
import huuloc.uit.edu.truyenqq.database.ImageChapRepository
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
import huuloc.uit.edu.truyenqq.database.QueueDownload
import huuloc.uit.edu.truyenqq.network.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.URL

class ServiceDownload : Service() {

    private val compo by lazy { CompositeDisposable() }
    private val apiManager: ApiManager by lazy { ApiManager() }
    var listQueue = mutableListOf<QueueDownload>()
    val repo: ImageChapRepository by lazy {
        ImageChapRepository(application)
    }
    lateinit var bookId: String
    lateinit var context: Context
    var number = 0
    var require = 0
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bookId = intent!!.getStringExtra("bookId")!!
        context = applicationContext
        AsyncTaskLoadQueue().execute()
        return START_STICKY
    }

    override fun onDestroy() {
        println("### ondestroy")
        super.onDestroy()
    }

    fun showNotification(message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = "channel_id"
        val defaultRingtone = RingtoneManager
            .getActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_RINGTONE
            )
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(defaultRingtone)
            .setContentIntent(pending)
            .setContentTitle("Test cái nhẹ")
            .setContentText(message)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                channelId,
                "TruyenQQ",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(chanel)
        }
        notificationManager.notify(
            1,
            notificationBuilder.build()
        )
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskLoad internal constructor(val context: Context, val chap: String, val list: List<String>) :
        AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            println("### Vào chap $chap")
            if (repo.findChapId(bookId, chap) == "0") {
                list.forEachIndexed { i, it ->
                    showNotification("Đang tải chap $chap($i/${list.size})")
                    val bitmap = getBitmapFromURL(it)
                    if (bitmap != null) {
                        try {
                            ImageStorageManager.saveToInternalStorage(context, bitmap, "$bookId+$chap+$i")
                            repo.insert(
                                listOf(
                                    ImageChap(
                                        bookId = bookId,
                                        chapId = chap,
                                        position = i,
                                        name = "$bookId+$chap+$i"
                                    )
                                )
                            )
                            println("### $bookId+$chap+$i")
                        } catch (ex: Exception) {

                        }
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            repo.deleteQueue(bookId, chap)
            number++
            if(number==require) {
                showNotification("Tải xuống hoàn thành")
                this@ServiceDownload.onDestroy()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskLoadQueue : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            listQueue.clear()
            listQueue.addAll(repo.getDataQueueById(bookId)!!)
            return null
        }

        @SuppressLint("CheckResult")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (!listQueue.isNullOrEmpty()) {
                require = listQueue.size
                for (i: QueueDownload in listQueue) {
                    apiManager.getListImage(bookId, i.chapId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ story ->
                            if (story.list != null) {
                                AsyncTaskLoad(context, story.order, story.list!!).execute()
                            }
                        }, {

                        })
                }
            }
        }
    }

    fun getBitmapFromURL(_url: String): Bitmap? {
        return try {
            val url = URL(_url)
            val connection = url.openConnection()
            connection.doInput = true
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            myBitmap
        } catch (ex: IOException) {
            null
        }
    }
}
