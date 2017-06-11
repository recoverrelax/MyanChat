package com.lolchat.myanmarking.myanchat.network.xmpp

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.lolchat.myanmarking.myanchat.io.interfaces.IFriendChangeListener
import com.lolchat.myanmarking.myanchat.io.interfaces.IXmppManager
import com.lolchat.myanmarking.myanchat.io.storage.prefs.PrefsUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.SmackConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

class XmppManager(
        val app: Application,
        val userPrefs: PrefsUser,
        val rosterManager: RiotRosterManager,
        val chatManager: RiotChatManager,
        val connManager: RiotConnManager
): IXmppManager {

    private val DEBUG: Boolean = true
    private var isBound = false

    private lateinit var mService: RiotXmppService
    private var xmppConn: AbstractXMPPConnection? = null

    private val isConnAndRosterReady: PublishSubject<Boolean> = PublishSubject.create<Boolean>()

    private val SERVER_PORT = 5223
    private val SERVER_DOMAIN = "pvp.net"
    private val MAX_CONNECTION_TRIES = 4

    fun isServiceRunning(): Boolean = RiotXmppService.isRunning

    private val serviceConn: ServiceConnection by lazy {
        object: ServiceConnection{
            override fun onServiceConnected(name: ComponentName, serviceBinder: IBinder) {
                val binder = serviceBinder as RiotXmppService.MyBinder
                mService = binder.service
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isBound = false
            }
        }
    }

    fun connectRiotXmppServer() {
        val username = userPrefs.username
        val password = userPrefs.password
        val serverHost = userPrefs.server

        val connConfig = XMPPTCPConnectionConfiguration.builder()
                .setSocketFactory(SSLSocketFactory.getDefault())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setXmppDomain(SERVER_DOMAIN)
                .setDebuggerEnabled(true)
                .setHost(serverHost.serverHost)
                .setUsernameAndPassword(username, "AIR_$password")
                .setPort(SERVER_PORT)
                .build()

        SmackConfiguration.setDefaultParsingExceptionCallback{}

        val conn = XMPPTCPConnection(connConfig)
        this.xmppConn = conn

        connect(conn)
                .flatMap { login(it) }
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { conn ->
                            rosterManager.init(conn)
                            chatManager.init(conn)
                            connManager.init(conn)
                        },
                        { Timber.e(it)}
                )
    }

    private fun connect(conn: AbstractXMPPConnection): Observable<AbstractXMPPConnection> {
        return Observable.fromCallable {
            conn.connect()
        }
    }
    private fun login(conn: AbstractXMPPConnection): Observable<AbstractXMPPConnection> {
        return Observable.fromCallable {
            conn.login()
            conn
        }
    }

    fun startAndBind(app: Application) {
        val intent = Intent(app, RiotXmppService::class.java)

        if(!isServiceRunning()){
            app.startService(intent)
            if(DEBUG) Timber.i("Service was started")
        }else {
            if(DEBUG) Timber.i("Service already running")
        }
        if(!isBound){
            val wasBound = app.bindService(intent, serviceConn, 0)
            if(DEBUG) Timber.i("Service was bound with result: $wasBound")
        }else{
            if(DEBUG) Timber.i("Service already bound")

        }
    }

    private fun connectedAndAuthenticated(): Boolean{
        val xmppConn = this.xmppConn ?: return false
        return xmppConn.isConnected && xmppConn.isAuthenticated
    }

    override fun addOnFriendChangeListener(listener: IFriendChangeListener){
        rosterManager.addOnFriendChangeListener(listener)
    }

    override fun removeOnFriendChangeListener(listener: IFriendChangeListener){
        rosterManager.removeOnFriendChangeListener(listener)
    }
}