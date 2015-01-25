package idle.land.app.logic;

import android.support.annotation.Nullable;

/**
 * Manager for user account
 */
public class AccountManager {

    private static AccountManager singelton;

    private Account mAcount;
    private Preferences mPreferences;

    /**
     * true if account should be stored in preferences
     */
    private boolean mRemember;

    public static AccountManager getInstance()
    {
        if(singelton == null)
            singelton = new AccountManager();
        return singelton;
    }

    private AccountManager()
    {
        mPreferences = new Preferences();
        mRemember = mPreferences.getBoolean(Preferences.Property.ACC_REMEMBER);
        if(mRemember)
            mAcount = loadAccountFromPrefs();
    }

    /**
     * gets the account from shared prefs
     * @return Account or null
     */
    private Account loadAccountFromPrefs()
    {
        if(!mPreferences.getString(Preferences.Property.ACC_USERNAME).isEmpty())
        {
            Account acc = new Account();
            acc.username = mPreferences.getString(Preferences.Property.ACC_USERNAME);
            acc.appName = mPreferences.getString(Preferences.Property.ACC_APPNAME);
            acc.password = mPreferences.getString(Preferences.Property.ACC_PASSWORD);
            acc.token = mPreferences.getString(Preferences.Property.ACC_AUTH_TOKEN);
            return acc;
        }
        return null;
    }

    /**
     * @return user account
     */
   public Account get()
    {
        return mAcount;
    }

    /**
     * Updates the user account with given values
     * @return true if the update was successful (can only update if account information are valid)
     */
    public boolean update(String username, String appName, String password, @Nullable String token)
    {
        Account acc = new Account();
        acc.username = username;
        acc.appName = appName;
        acc.password = password;
        acc.token = token;
        if(acc.valid()) {
            mAcount = acc;
            if(mRemember)
                saveAccountToPrefs();
            return true;
        }
        return false;
    }

    public boolean update(String identifier, String appName, String password)
    {
        return update(identifier, appName, password, null);
    }

    /**
     * updates the account token
     */
    public void updateToken(@Nullable String token)
    {
        if(mAcount != null) {
            mAcount.token = token;
            if(mRemember)
                saveAccountToPrefs();
        }
    }

    /**
     * Changes state of the account remember and saves/deletes the current acc from preferences accordingly
     */
    public void setRemember(boolean remember) {
        mRemember = remember;
        if(remember && mAcount.valid())
            saveAccountToPrefs();
        else
            deleteAccountFromPrefs();
    }

    /**
     * saves current account to preferences
     */
    private void saveAccountToPrefs()
    {
        mPreferences.putString(Preferences.Property.ACC_USERNAME, mAcount.username);
        mPreferences.putString(Preferences.Property.ACC_APPNAME, mAcount.appName);
        mPreferences.putString(Preferences.Property.ACC_PASSWORD, mAcount.password);
        mPreferences.putString(Preferences.Property.ACC_AUTH_TOKEN, mAcount.token);
        mPreferences.putBoolean(Preferences.Property.ACC_REMEMBER, true);
    }

    /**
     * deletes current account from preferences
     */
    private void deleteAccountFromPrefs()
    {
        mPreferences.putBoolean(Preferences.Property.ACC_REMEMBER, false);
        mPreferences.delete(Preferences.Property.ACC_USERNAME);
        mPreferences.delete(Preferences.Property.ACC_APPNAME);
        mPreferences.delete(Preferences.Property.ACC_PASSWORD);
        mPreferences.delete(Preferences.Property.ACC_AUTH_TOKEN);
    }

    /**
     * User Account
     */
    public class Account {
        public String username;
        public String appName;
        public String password;
        public String token;

        /**
         * A valid account has a non empty username, password and appname
         * @return true if account is valid
         */
        public boolean valid()
        {
            return !(username.isEmpty() || appName.isEmpty() || password.isEmpty());
        }

        /**
         * @return true if we have a valid token
         */
        public boolean isConnected()
        {
            return (token != null && !token.isEmpty());
        }

        /**
         * @return user identifer: appname#username
         * @see #username
         * @see #appName
         */
        public String getIdentifier()
        {
            return appName + "#" + username;
        }
    }

}
