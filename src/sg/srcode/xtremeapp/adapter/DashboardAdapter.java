package sg.srcode.xtremeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.item.DashboardItem;

import java.util.ArrayList;

public class DashboardAdapter extends BaseAdapter {

    protected ArrayList<DashboardItem> mItems;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public DashboardAdapter(Context context, ArrayList<DashboardItem> items) {
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
            view = this.mLayoutInflater.inflate(R.layout.dashboard_item, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_dashboard_icon);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_dashboard_label);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DashboardItem item = this.mItems.get(i);
        viewHolder.textView.setText(item.getName());
        if(item.getIcon() != null) {
            viewHolder.imageView.setImageDrawable(item.getIcon());
        }

        return view;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}