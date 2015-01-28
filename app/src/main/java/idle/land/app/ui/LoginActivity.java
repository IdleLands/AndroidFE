package idle.land.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.AccountManager;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Preferences;
import idle.land.app.logic.api.HeartbeatService;
import idle.land.app.logic.api.apievents.ErrorEvent;
import idle.land.app.logic.api.apievents.HeartbeatEvent;
import idle.land.app.ui.dialogs.PendingLoginDialog;

public class LoginActivity extends ActionBarActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @InjectView(R.id.etLoginName)
    EditText etLoginName;

    @InjectView(R.id.etLoginPassword)
    EditText etLoginPassword;

    @InjectView(R.id.cbLoginRemember)
    CheckBox cbRemember;

    AccountManager mAccountManager;
    Preferences mPrefs;

    boolean remember;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frag);
        getSupportActionBar().hide();
        ButterKnife.inject(this);

        mAccountManager = AccountManager.getInstance();
        mPrefs = new Preferences();
        remember = mPrefs.getBoolean(Preferences.Property.ACC_REMEMBER);
        cbRemember.setChecked(remember);
        cbRemember.setOnCheckedChangeListener(this);
        if(remember)
        {
            AccountManager.Account account = mAccountManager.get();
            if(account != null)
            {
                if(account.appName.equals(getString(R.string.app_ident)))
                    etLoginName.setText(account.username);
                else
                    etLoginName.setText(account.getIdentifier());
                etLoginPassword.setText(account.password);
            }
        }
    }

    @OnClick(R.id.btLoginLogin)
    public void onLoginButtonClick()
    {
        login();
    }

    @OnClick(R.id.btLoginRegister)
    public void onRegisterButtonClick()
    {
        Toast.makeText(this, "Not implemented.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        remember = isChecked;
    }

    /**
     * performs the login operation
     */
    private void login()
    {
        // parse login information
        String identifier = etLoginName.getText().toString().trim();
        String username;
        String appName;
        if(identifier.contains("#"))
        {
            String[] split = identifier.split("#", 2);
            appName = split[0];
            username = split[1];
        } else
        {
            username = identifier;
            appName = getString(R.string.app_ident);
        }
        String password = etLoginPassword.getText().toString().trim();

        // update account information und start the login process
        if(mAccountManager.update(username, appName, password))
        {
            mAccountManager.setRemember(remember);
            mAccountManager.updateToken(null); // reset token on login
            new PendingLoginDialog().show(getSupportFragmentManager(), PendingLoginDialog.TAG);
            startService(new Intent(LoginActivity.this, HeartbeatService.class));
        }
        else
            Toast.makeText(this, getString(R.string.login_error_no_username_or_no_password), Toast.LENGTH_SHORT).show();
    }

    private void openMainActivity()
    {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        BusProvider.getInstance().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onHeartbeatEvent(HeartbeatEvent event)
    {
        if(event.isSuccessful())
            openMainActivity();
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent e)
    {
        switch(e.code)
        {
            case Badpassword:
            case Nopasswordspecified:
                Toast.makeText(this, getString(R.string.login_error_wrong_password), Toast.LENGTH_SHORT).show();
                break;
            case Playerdoesnotexist:
            case Playerdoesntexist:
                Toast.makeText(this, getString(R.string.login_error_wrong_username), Toast.LENGTH_SHORT).show();
                break;
            case Onlyoneturninthetimelimit:
            case Badtoken:
            default:
                Toast.makeText(this, getString(R.string.login_error_unknown), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
