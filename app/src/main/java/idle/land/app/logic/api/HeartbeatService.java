package idle.land.app.logic.api;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.squareup.otto.Produce;
import idle.land.app.logic.AccountManager;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Logger;
import idle.land.app.logic.Model.Player;
import idle.land.app.logic.api.apievents.*;

/**
 * Service to perform turns every few seconds and receive updated player information
 */
public class HeartbeatService extends Service implements Runnable {

    public static final String TAG = HeartbeatService.class.getSimpleName();

    /**
     * time interval between heartbeats
     */
    public static final int HEARTBEAT_INTERVAL_MILLISECONDS = 15 * 1000;

    /**
     * ID of the foreground statusbar notification
     */
    public static final int NOTIFICATION_ID = 17;

    /**
     * Intent extra to stop the service
     */
    public static final String EXTRA_STOP = "stop";

    private AccountManager mAccountManager;
    private ApiConnection mApiConnection;
    private NotificationManager mNotificationManager;

    /**
     * the last event the service sent out
     */
    private AbstractHeartbeatEvent lastEvent;
    private ServiceHeartbeatCallback cb;

    Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        BusProvider.getInstance().register(this);
        mAccountManager = AccountManager.getInstance();
        mApiConnection = new ApiConnection();
        mNotificationManager = NotificationManager.getInstance();
        mHandler = new Handler();
        cb = new ServiceHeartbeatCallback();

        // start in forground. This will run until user shuts it down
        startForeground(NOTIFICATION_ID, mNotificationManager.buildServiceStatusNotification(this));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null)
        {
            if(intent.hasExtra(EXTRA_STOP) && intent.getBooleanExtra(EXTRA_STOP, false))
                logout();
            else
                start();
        }
        return START_STICKY;
    }

    /**
     * starts the heartbeat
     */
    private void start()
    {
        mHandler.post(this);
    }


    /**
     * stops the heartbeat and service
     */
    private void stopHeartbeat()
    {
        mHandler.removeCallbacksAndMessages(null);
        stopSelf();
    }

    /**
     * performs a logout and stops the service
     * We dont care about the result of that call, so its asynchron w/o callback
     */
    private void logout()
    {
        stopHeartbeat();
        AccountManager.Account acc = mAccountManager.get();
        mApiConnection.logout(acc.getIdentifier(), acc.token);
        postEvent(new LogoutEvent());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * starts a new delayed Heartbeat
     */
    private void startDelayedHeartbeat(int waitTime) {
        mHandler.postDelayed(this, waitTime);
    }

    /**
     * invoked at each heartbeat
     * performas a turn or login
     */
    @Override
    public void run() {
        AccountManager.Account account = mAccountManager.get();
        if(account.isConnected())
            mApiConnection.turn(account.getIdentifier(), account.token, cb);
        else
            mApiConnection.login(account.getIdentifier(), account.password, cb);
    }


    @Produce
    public AbstractHeartbeatEvent produceStatusEvent()
    {
        return lastEvent;
    }

    /**
     * Posts a new event to the bus and notifications
     */
    public void postEvent(AbstractHeartbeatEvent event)
    {
        lastEvent = event;
        BusProvider.getInstance().post(lastEvent);
        if(event instanceof HeartbeatEvent && ((HeartbeatEvent) event).isSuccessful())
            mNotificationManager.postEventNotification(this, ((HeartbeatEvent) event).player.getRecentEvents());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    /**
     * Callback for heartbeat events
     */
    public class ServiceHeartbeatCallback extends HeartbeatCallback
    {

        @Override
        public void onHeartbeatSuccess(StatusCode code, Player player) {
            // post resulting player and prepare for next heartbeat
            postEvent(new HeartbeatEvent(code, player));
            startDelayedHeartbeat(HEARTBEAT_INTERVAL_MILLISECONDS);
        }

        @Override
        public void onLoginSuccess(StatusCode code, Player player, String token) {
            // save the token and make a successful bear
            mAccountManager.updateToken(token);
            postEvent(new LoginEvent(code, player));
            startDelayedHeartbeat(HEARTBEAT_INTERVAL_MILLISECONDS);
        }

        @Override
        public void onError(StatusCode error) {
            Logger.warn(TAG, error.toString());
            switch (error)
            {
                case Onlyoneturninthetimelimit:
                    startDelayedHeartbeat(HEARTBEAT_INTERVAL_MILLISECONDS/2);
                    break;
                case Badtoken:
                    mAccountManager.updateToken(null);
                    startDelayedHeartbeat(HEARTBEAT_INTERVAL_MILLISECONDS/2);
                    break;
                // login issues or sth. unexpected, user should relog
                case Badpassword:
                case Nopasswordspecified:
                case Playerdoesnotexist:
                default:
                    mAccountManager.updateToken(null);
                    postEvent(new ErrorEvent(error));
                    stopHeartbeat();
                    break;
            }
        }

    }


}
