package sg.srcode.xtremeapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.utils.ColorUtils;

import java.util.ArrayList;

public class CarparkAdapter extends BaseAdapter {

    protected ArrayList<CarparkItem> mItems;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public CarparkAdapter(Context context, ArrayList<CarparkItem> items) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
    }

    public int getCount() {
        return this.mItems.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getItem(int i) {
        return this.mItems.get(i);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getItemId(int i) {
        return i;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            view = this.mLayoutInflater.inflate(R.layout.carpark_item, null);

            viewHolder = new ViewHolder();
            viewHolder.status = (ImageView) view.findViewById(R.id.tv_carpark_status);
            viewHolder.name = (TextView) view.findViewById(R.id.tv_carpark_name);
            viewHolder.area = (TextView) view.findViewById(R.id.tv_carpark_area);
            viewHolder.lots = (TextView) view.findViewById(R.id.tv_carpark_freelots);
            viewHolder.charge = (TextView) view.findViewById(R.id.tv_carpark_charge);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CarparkItem item = this.mItems.get(i);
        viewHolder.status.setBackgroundColor(mContext.getResources().getColor(
                ColorUtils.mapColorToValue(item.getmAvailability())));
        viewHolder.name.setText(item.getmDevelopment());
        viewHolder.area.setText(item.getmArea());
        viewHolder.lots.setText(", "+item.getmFreeLots()+" ");
        viewHolder.charge.setText("$"+item.getmBaseCharge());

        return view;
    }

    static class ViewHolder {
        ImageView status;
        TextView name;
        TextView area;
        TextView lots;
        TextView charge;
    }
}