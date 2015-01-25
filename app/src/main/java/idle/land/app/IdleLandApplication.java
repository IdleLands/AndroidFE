package idle.land.app;

import android.app.Application;

public class IdleLandApplication extends Application {

    private static IdleLandApplication singelton;

    @Override
    public void onCreate() {
        singelton = this;
    }

    public static IdleLandApplication getInstance()
    {
        return singelton;
    }
}
