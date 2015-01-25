package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.joanzapata.android.iconify.IconDrawable;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Model.Event;
import idle.land.app.logic.api.HeartbeatEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdventureLogFragment extends ListFragment {

    public static AdventureLogFragment newInstance()
    {
        return new AdventureLogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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

    @Subscribe
    public void onHeartbeatEvent(HeartbeatEvent event)
    {
        if(event.player != null)
            setListAdapter(new AdventureLogAdapter(event.player.getRecentEvents()));
    }

    class AdventureLogAdapter extends BaseAdapter
    {

        List<Event> events;

        public AdventureLogAdapter(List<Event> e)
        {
            events = new ArrayList<Event>();
            events.addAll(e);
            Collections.reverse(events);
        }

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int position) {
            return events.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        class ViewHolder
        {
            TextView tvTime;
            TextView tvMessage;
            ImageView ivIcon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adventure_log_event, parent, false);
                holder = new ViewHolder();
                holder.tvTime = (TextView) convertView.findViewById(R.id.tvAdventureDate);
                holder.tvMessage = (TextView) convertView.findViewById(R.id.tvAdventureMessage);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivAdventureIcon);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            Event event = (Event) getItem(position);
            holder.tvMessage.setText(event.getMessage());
            holder.tvTime.setText(DateUtils.getRelativeDateTimeString(getActivity(), event.getCreatedAt().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
            holder.ivIcon.setImageDrawable(new IconDrawable(getActivity(), event.getType().getIconValue()));
            return convertView;
        }
    }

}
