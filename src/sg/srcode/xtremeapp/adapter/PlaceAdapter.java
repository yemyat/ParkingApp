package sg.srcode.xtremeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.item.PlaceItem;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter {

    protected ArrayList<PlaceItem> mItems;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public PlaceAdapter(Context context, ArrayList<PlaceItem> items) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;

    }

    public int getCount() {
        return this.mItems.size();
    }

    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            view = this.mLayoutInflater.inflate(R.layout.place_item, null);

            viewHolder = new ViewHolder();
           // viewHolder.avatar = (ImageView)view.findViewById(R.id.iv_place_avatar);
            viewHolder.name = (TextView)view.findViewById(R.id.tv_place_name);
            viewHolder.operatingHours = (TextView)view.findViewById(R.id.tv_operating_hours);
            viewHolder.distance = (TextView)view.findViewById(R.id.tv_distance);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        PlaceItem item = this.mItems.get(i);
        viewHolder.name.setText(item.getmName());
        viewHolder.operatingHours.setText(item.getmOperatingHrs());
        viewHolder.distance.setText(item.getmDistance());
        return view;
    }
        static class ViewHolder {
        //ImageView avatar;
        TextView name;
        TextView operatingHours;
        TextView distance;

    }
}
