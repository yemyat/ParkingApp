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
import sg.srcode.xtremeapp.item.EditableItem;

import java.util.ArrayList;

public class EditableCellAdapter extends BaseAdapter {

    protected ArrayList<EditableItem> mItems;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public EditableCellAdapter(Context context, ArrayList<EditableItem> items) {
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
            view = this.mLayoutInflater.inflate(R.layout.editable_cell_item, null);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.iv_cell_icon);
            viewHolder.label = (TextView) view.findViewById(R.id.tv_cell_title);
            viewHolder.value = (TextView) view.findViewById(R.id.tv_cell_value);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        EditableItem item = this.mItems.get(i);
        viewHolder.image.setImageDrawable(item.getIcon());
        viewHolder.label.setText(item.getName());
        viewHolder.value.setText(item.getmValue());

        return view;
    }

    static class ViewHolder {
        ImageView image;
        TextView label;
        TextView value;
    }
}