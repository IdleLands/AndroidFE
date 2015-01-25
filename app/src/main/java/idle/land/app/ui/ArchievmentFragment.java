package idle.land.app.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.*;
import com.joanzapata.android.iconify.IconDrawable;
import com.squareup.otto.Subscribe;
import idle.land.app.R;
import idle.land.app.logic.BusProvider;
import idle.land.app.logic.Model.Archievment;
import idle.land.app.logic.api.HeartbeatEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArchievmentFragment extends ListFragment {

    public static ArchievmentFragment newInstance()
    {
        return new ArchievmentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.archievment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sort_by_name)
            ((Filterable) getListView().getAdapter()).getFilter().filter("byname");
        else if(item.getItemId() == R.id.menu_sort_by_type)
            ((Filterable) getListView().getAdapter()).getFilter().filter("bytype");

        return super.onOptionsItemSelected(item);
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
        {
            List<Archievment> archievments = event.player.getAchievements();

            if(getListAdapter() == null)
                setListAdapter(new ArchvimentListAdapter(archievments));
            else
                ((ArchvimentListAdapter) getListAdapter()).updateEvents(archievments);
        }
    }

    class ArchvimentListAdapter extends BaseAdapter implements Filterable
    {
        List<Archievment> archievments;
        String lastSort;

        public ArchvimentListAdapter(List<Archievment> a)
        {
            archievments = new ArrayList<Archievment>();
            archievments.addAll(a);

        }

        /**
         * Replaces the list of archivments with the new one if they differ
         * @return true if something changed
         */
        public boolean updateEvents(List<Archievment> newArchievments)
        {
            if(newArchievments.containsAll(archievments) && archievments.containsAll(newArchievments))
                return false;

            archievments = newArchievments;
            if(!lastSort.isEmpty())
                getFilter().filter(lastSort);
            else
                notifyDataSetChanged();

            if(getCount() > 0)
                getListView().smoothScrollToPosition(0);
            return true;
        }

              @Override
        public int getCount() {
            return archievments.size();
        }

        @Override
        public Object getItem(int position) {
            return archievments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<Archievment> sortedArchievments = new ArrayList<Archievment>();
                    sortedArchievments.addAll(archievments);
                    if(constraint.toString().equals("byname"))
                    {
                        Collections.sort(sortedArchievments, new Comparator<Archievment>() {
                            @Override
                            public int compare(Archievment lhs, Archievment rhs) {
                                return lhs.getName().compareTo(rhs.getName());
                            }
                        });
                    } else {
                        Collections.sort(sortedArchievments, new Comparator<Archievment>() {
                            @Override
                            public int compare(Archievment lhs, Archievment rhs) {
                                return lhs.getType().compareTo(rhs.getType());
                            }
                        });
                    }
                    FilterResults results = new FilterResults();
                    results.values = sortedArchievments;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    archievments = (List<Archievment>) results.values;
                    lastSort = constraint.toString();
                    notifyDataSetChanged();
                }
            };
        }

        class ViewHolder
        {
            TextView tvTitle, tvMessage, tvReward;
            ImageView ivIcon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.archievment_item, parent, false);
                convertView.setTag(holder);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tvArchievmentTitle);
                holder.tvMessage = (TextView) convertView.findViewById(R.id.tvArchievmentMessage);
                holder.tvReward = (TextView) convertView.findViewById(R.id.tvArchievmentReward);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivArchievmentIcon);

            } else
                holder = (ViewHolder) convertView.getTag();

            Archievment a = (Archievment) getItem(position);
            holder.tvTitle.setText(a.getName());
            holder.tvMessage.setText(a.getDesc());
            holder.tvReward.setText(a.getReward());
            holder.ivIcon.setImageDrawable(new IconDrawable(getActivity(), a.getType().getIconValue()));

            return convertView;
        }
    }

}
