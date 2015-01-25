package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import idle.land.app.R;

public class OverviewFragment extends Fragment {

    public static OverviewFragment newInstance()
    {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.overview_frag, container, false);

        return v;
    }
}
