package idle.land.app.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import idle.land.app.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
