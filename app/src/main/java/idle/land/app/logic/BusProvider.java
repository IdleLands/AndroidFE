package idle.land.app.logic;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;

public class BusProvider {

    private static Bus bus;

    public static Bus getInstance() {
        if (bus == null)
            bus = new MainThreadBus();
        return bus;
    }

    static class MainThreadBus extends Bus {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainThreadBus.super.post(event);
                    }
                });
            }
        }
    }
}
