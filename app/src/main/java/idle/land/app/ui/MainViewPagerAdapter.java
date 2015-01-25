package idle.land.app.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import idle.land.app.R;

/**
 * ViewPager adapter for MainActivity
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public MainViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.main_activity_tab_adventure_log);
    }

    @Override
    public Fragment getItem(int position) {
        return AdventureLogFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
