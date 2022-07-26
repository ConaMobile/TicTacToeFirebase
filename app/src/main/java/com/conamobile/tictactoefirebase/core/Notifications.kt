package com.conamobile.tictactoefirebase.core

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.conamobile.tictactoefirebase.R
import com.conamobile.tictactoefirebase.ui.MainActivity

class Notifications {

    private val tag = "new request"
    fun notify(context: Context, message: String, number: Int) {
        val intent = Intent(context, MainActivity::class.java)

        val builder = NotificationCompat.Builder(context)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("New request")
            .setContentText(message)
            .setNumber(number)
            .setSmallIcon(R.drawable.tic_tac_toe)
            .setContentIntent(PendingIntent.getActivity(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(true)

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        nm.notify(tag, 0, builder.build())

    }

}