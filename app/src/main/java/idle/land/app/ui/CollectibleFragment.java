package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Model.Collectible;
import idle.land.app.logic.api.apievents.HeartbeatEvent;

import java.util.List;

public class CollectibleFragment extends ListFragment {

    public static CollectibleFragment newInstance()
    {
        return new CollectibleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  super.onCreateView(inflater, container, savedInstanceState);

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        listView.setPadding(margin, margin, margin, margin);
        listView.setClipToPadding(false);
        listView.setDivider(null);
        listView.setDividerHeight(margin);

        return v;
    }

    @Subscribe
    public void onHeartbeat(HeartbeatEvent e)
    {
        if(e.isSuccessful())
            updateCollectibles(e.player.getCollectibles());
    }

    private void updateCollectibles(List<Collectible> collectibles)
    {
        if(getListAdapter() != null)
        {
            // TODO update old adapter
        } else
            setListAdapter(new CollectibleListAdapter(collectibles));
    }

    @Override
    public void onPause() {
        BusProvider.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    class CollectibleListAdapter extends BaseAdapter
    {

        List<Collectible> collectibles;

        public CollectibleListAdapter(List<Collectible> collectibles)
        {
            this.collectibles = collectibles;
        }

        @Override
        public int getCount() {
            return collectibles.size();
        }

        @Override
        public Object getItem(int position) {
            return collectibles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder
        {
            TextView tvName, tvDescription, tvRarity;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.collectible, parent, false);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvCollectibleName);
                holder.tvDescription = (TextView) convertView.findViewById(R.id.tvCollectibleDescription);
                holder.tvRarity = (TextView) convertView.findViewById(R.id.tvCollectibleRarity);
            } else
                holder = (ViewHolder) convertView.getTag();

            Collectible c = (Collectible) getItem(position);
            holder.tvName.setText(c.getName());
            holder.tvRarity.setText(c.getRarity().substring(0,1).toUpperCase() + c.getRarity().substring(1));
            String description = "Found %1$s in %2$s (%3$s)";
            holder.tvDescription.setText(String.format(
                    description,
                    DateUtils.getRelativeDateTimeString(getActivity(), c.getFoundAt(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, 0),
                    c.getMap(),
                    c.getRegion()
            ));

            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }
}
