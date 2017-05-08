package com.lolchat.myanmarking.myanchat.network.xmpp

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.lolchat.myanmarking.myanchat.MyApp
import javax.inject.Inject
import android.app.PendingIntent
import com.lolchat.myanmarking.myanchat.R


class RiotXmppService : Service() {

    private val ONGOING_XMPP_ID = 1276347
    private val binder = MyBinder()
    override fun onBind(intent: Intent?): IBinder = binder
    private var isBound: Boolean = false

    @Inject lateinit var xmppManager: XmppManager

    companion object{
        var isRunning: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        MyApp.INSTANCE.appComponent.inject(this)
        isRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        xmppManager.connectRiotXmppServer()

        val notification = Notification.Builder(this)
                .setContentTitle("RiotXmppService")
                .setContentText("Description")
                .setSmallIcon(R.drawable.abc_ab_share_pack_mtrl_alpha)
                .build()

        startForeground(ONGOING_XMPP_ID, notification)

        return Service.START_STICKY
    }

    inner class MyBinder : Binder() {
        val service: RiotXmppService
            get() = this@RiotXmppService
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }
}