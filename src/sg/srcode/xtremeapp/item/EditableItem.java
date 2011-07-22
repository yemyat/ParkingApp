package sg.srcode.xtremeapp.item;

import android.graphics.drawable.Drawable;

public class EditableItem {

    protected String mName;
    protected Drawable mIcon;
    protected String mValue;

    public EditableItem(String name, String value, Drawable icon) {
        this.mName = name;
        this.mValue = value;
        this.mIcon = icon;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }
}
