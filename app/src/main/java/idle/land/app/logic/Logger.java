package idle.land.app.logic;

import android.util.Log;
import idle.land.app.BuildConfig;

public class Logger {

    public static void debug(String tag, String text)
    {
        if(BuildConfig.DEBUG)
            Log.d(tag, text);
    }

    public static void warn(String tag, String text)
    {
        if(BuildConfig.DEBUG)
            Log.w(tag, text);
    }

}
