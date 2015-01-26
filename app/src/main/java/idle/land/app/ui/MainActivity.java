package idle.land.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.otto.Subscribe;
import com.viewpagerindicator.TitlePageIndicator;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Model.Player;
import idle.land.app.logic.api.HeartbeatEvent;
import idle.land.app.logic.api.HeartbeatService;
import idle.land.app.logic.api.NotificationManager;
import idle.land.app.ui.views.HeartbeatTicker;

public class MainActivity extends ActionBarActivity {

    /**
     * Ticker showing time to next heartbeat
     */
    @InjectView(R.id.heartbeatTicker)
    HeartbeatTicker mHeartbeatTicker;

    @InjectView(R.id.viewPagerIndicator)
    TitlePageIndicator mTitlePagerIndicator;

    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        mViewPager.setAdapter(new MainViewPagerAdapter(this, getSupportFragmentManager()));
        mTitlePagerIndicator.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        NotificationManager.getInstance().setActive(true);
    }

    /**
     * bring user to login activity
     */
    public void showLogin()
    {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Subscribe
    public void onStatusEvent(HeartbeatEvent event)
    {
        mHeartbeatTicker.reset();
        switch(event.type)
        {
            case LOGGED_IN:
                onNewPlayerEvent(event.player);
                break;
            case HEARTBEAT:
                onNewPlayerEvent(event.player);
                break;
            case LOGGED_OUT:
                Toast.makeText(this, getString(R.string.toast_logged_out), Toast.LENGTH_SHORT).show();
                showLogin();
                break;
            case ERROR:
                Toast.makeText(this, getString(R.string.toast_something_went_wrong), Toast.LENGTH_LONG).show();
                showLogin();
                break;
        }
    }

    void onNewPlayerEvent(Player newPlayer)
    {
        String title = getString(R.string.main_actionbar_title);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setTitle( String.format(title, newPlayer.getName(), newPlayer.getProfessionName()) );
    }

    @Override
    protected void onPause() {
        BusProvider.getInstance().unregister(this);
        mHeartbeatTicker.stop();
        NotificationManager.getInstance().setActive(false);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_logout:
                Intent logoutIntent = new Intent(this, HeartbeatService.class);
                logoutIntent.putExtra(HeartbeatService.EXTRA_STOP, true);
                startService(logoutIntent);
                break;
            case R.id.menu_settings:
                Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
