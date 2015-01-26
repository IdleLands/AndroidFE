package idle.land.app.logic.api;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.squareup.otto.Produce;
import idle.land.app.logic.AccountManager;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Logger;
import idle.land.app.logic.Model.Player;

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
    private HeartbeatEvent lastEvent;
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
                stop();
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
     * stops the heartbeat and service without an error
     */
    private void stop() {stop(false); }

    /**
     * stops the heartbeat and service
     * posts a logged out event
     * @param error true if an error event should be posted instead of logged out
     */
    private void stop(boolean error)
    {
        mHandler.removeCallbacksAndMessages(null);
        AccountManager.Account acc = mAccountManager.get();
        mApiConnection.logout(acc.getIdentifier(), acc.token);
        if(error)
        {
            postEvent(HeartbeatEvent.EventType.ERROR, null);
        } else
        {
            postEvent(HeartbeatEvent.EventType.LOGGED_OUT, null);
        }
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * starts a new delayed Heartbeat
     */
    private void startDelayedHeartbeat() {
        mHandler.postDelayed(this, HEARTBEAT_INTERVAL_MILLISECONDS);
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
    public HeartbeatEvent produceStatusEvent()
    {
        return lastEvent;
    }

    /**
     * Posts a new event to the bus
     * @param type of the event
     * @param player new player object or null if the event doesnt carry any
     */
    public void postEvent(HeartbeatEvent.EventType type, @Nullable Player player)
    {
        if(lastEvent == null)
        {
            lastEvent = new HeartbeatEvent(type, player);
        } else
        {
            lastEvent.type = type;
            lastEvent.player = player;
        }
        BusProvider.getInstance().post(lastEvent);
        if(player != null)
            mNotificationManager.postEventNotification(this, player.getRecentEvents());
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
        public void onHeartbeatSuccess(Player player) {
            // post heartbeat result
            postEvent(HeartbeatEvent.EventType.HEARTBEAT, player);
            // start next heartbeat
            startDelayedHeartbeat();
        }

        @Override
        public void onLoginSuccess(Player player, String token) {
            mAccountManager.updateToken(token);
            postEvent(HeartbeatEvent.EventType.LOGGED_IN, player);

            // start next heartbeat
            startDelayedHeartbeat();
        }

        @Override
        public void onError(ErrorType error) {
            Logger.warn(TAG, error.toString());
            switch (error)
            {
                // we ignore this if we are already logged in. if not this is serious
                case TOO_MANY_TRIES:
                    if(mAccountManager.get().token.isEmpty())
                        stop(true);
                    else
                        startDelayedHeartbeat();
                    break;

                // those are serious
                // let user relog manually
                case NO_NAME_SPECIFIED:
                case CANT_AUTH_VIA_PASSWORD:
                case PLAYER_DOESNT_EXIST:
                case BAD_PASSWORD:
                case UNKNOWN:
                    mAccountManager.updateToken(null);
                    stop(true);
                    break;
                // relog on the rest
                default:
                    mAccountManager.updateToken(null);
                    startDelayedHeartbeat();
                    break;
            }
        }

    }


}
