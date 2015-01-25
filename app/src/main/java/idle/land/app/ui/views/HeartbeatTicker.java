package idle.land.app.ui.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import idle.land.app.R;
import idle.land.app.logic.api.HeartbeatService;

public class HeartbeatTicker extends ProgressBar {

    Handler handler = new Handler();
    boolean mRunning = false;

    static final int MAX_VALUE = HeartbeatService.HEARTBEAT_INTERVAL_MILLISECONDS;
    static final int CYCLE_TIME_MILLIS = HeartbeatService.HEARTBEAT_INTERVAL_MILLISECONDS;
    static final int TICK_TIME_MILLIS = 50;
    static final int TICKS_PER_CYCLE = CYCLE_TIME_MILLIS / TICK_TIME_MILLIS;
    static final int INCREMENT = MAX_VALUE / TICKS_PER_CYCLE;

    public HeartbeatTicker(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.TickerStyle);
        setMax(MAX_VALUE);
    }

    public void start()
    {
        mRunning = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                incrementProgressBy(INCREMENT);
                handler.postDelayed(this, TICK_TIME_MILLIS);
            }
        }, TICK_TIME_MILLIS);
    }

    public void stop()
    {
        handler.removeCallbacksAndMessages(null);
        mRunning = false;
    }

    public void reset()
    {
        if(!isRunning())
            start();
        setProgress(0);
    }

    public boolean isRunning()
    {
        return mRunning;
    }
}
