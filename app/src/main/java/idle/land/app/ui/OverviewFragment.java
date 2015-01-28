package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.api.apievents.HeartbeatEvent;
import idle.land.app.ui.views.BaseInformationCard;
import idle.land.app.ui.views.BaseStatsCard;
import idle.land.app.ui.views.ExtendedStatsCard;

public class OverviewFragment extends Fragment {

    public static OverviewFragment newInstance()
    {
        return new OverviewFragment();
    }

    BaseInformationCard mBaseInformationCard;
    BaseStatsCard mBaseStatsCard;
    ExtendedStatsCard mExtendedStatsCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.overview_frag, container, false);
        mBaseInformationCard = (BaseInformationCard) v.findViewById(R.id.baseInformationCard);
        mBaseStatsCard = (BaseStatsCard) v.findViewById(R.id.baseStatsCard);
        mExtendedStatsCard = (ExtendedStatsCard) v.findViewById(R.id.extendedStatsCard);
        return v;
    }

    @Subscribe
    public void onNewHeartbeatEvent(HeartbeatEvent event)
    {
        if(event.isSuccessful())
        {
            mBaseInformationCard.setPlayer(event.player);
            mBaseStatsCard.setPlayer(event.player);
            mExtendedStatsCard.setPlayer(event.player);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        BusProvider.getInstance().unregister(this);
        super.onPause();
    }
}
