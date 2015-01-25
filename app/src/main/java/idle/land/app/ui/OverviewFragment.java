package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.api.HeartbeatEvent;
import idle.land.app.ui.views.BaseInformationCard;

public class OverviewFragment extends Fragment {

    public static OverviewFragment newInstance()
    {
        return new OverviewFragment();
    }

    BaseInformationCard mBaseInformationCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.overview_frag, container, false);
        mBaseInformationCard = (BaseInformationCard) v.findViewById(R.id.baseInformationCard);
        return v;
    }

    @Subscribe
    public void onNewHeartbeatEvent(HeartbeatEvent event)
    {
        if(event.player != null)
        {
            mBaseInformationCard.setPlayer(event.player);
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
