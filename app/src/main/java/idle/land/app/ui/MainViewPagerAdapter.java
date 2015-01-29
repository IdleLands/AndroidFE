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
        switch (position)
        {
            case 0: return mContext.getString(R.string.main_activity_tab_overview);
            case 1: return mContext.getString(R.string.main_activity_tab_adventure_log);
            case 2: return mContext.getString(R.string.main_activity_tab_collectibles);
            default: return mContext.getString(R.string.main_activity_tab_archievments);
        }

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return OverviewFragment.newInstance();
            case 1:
                return AdventureLogFragment.newInstance();
            case 2:
                return CollectibleFragment.newInstance();
            default:
                return ArchievmentFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
