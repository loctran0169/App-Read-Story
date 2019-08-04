package huuloc.uit.edu.truyenqq.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.MainActivity

class FCMservies : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        println("### received ${p0?.data} + ${p0?.notification?.title}+${p0?.notification?.body}")
        sendNotification(p0!!.notification!!)

    }

    private fun sendNotification(notice: RemoteMessage.Notification) {
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
                RingtoneManager.TYPE_NOTIFICATION
            )
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(defaultRingtone)
            .setContentIntent(pending)
            .setContentTitle(notice.title)
            .setContentText(notice.body)
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
            System.currentTimeMillis().toInt(),
            notificationBuilder.build()
        )
        println("### received ${notice.title} + ${notice.title}+${notice.body}")
    }
}