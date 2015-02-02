package idle.land.app.logic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import idle.land.app.IdleLandApplication;

public class Preferences {

    public enum Property
    {
        ACC_USERNAME, ACC_APPNAME, ACC_PASSWORD, ACC_AUTH_TOKEN, ACC_REMEMBER,
        NOTIFICATION_PLAY_SOUND, SERVICE_WIFI_MODE
    }

    SharedPreferences prefs;

    public Preferences()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(IdleLandApplication.getInstance());
    }

    public String getString(Property prop)
    {
        return prefs.getString(prop.toString(), null);
    }

    public void putString(Property prop, String string)
    {
        SharedPreferences.Editor e = prefs.edit();
        e.putString(prop.toString(), string);
        e.commit();
    }

    public boolean getBoolean(Property prop) {return prefs.getBoolean(prop.toString(), false); }
    public void putBoolean(Property prop, boolean b)
    {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(prop.toString(), b);
        e.commit();
    }

    public void delete(Property prop)
    {
        SharedPreferences.Editor e = prefs.edit();
        e.remove(prop.toString());
    }

}
