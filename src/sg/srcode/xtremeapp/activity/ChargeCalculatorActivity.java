package sg.srcode.xtremeapp.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import greendroid.app.GDActivity;
import greendroid.widget.NormalActionBarItem;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.EditableCellAdapter;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.item.EditableItem;

import java.util.ArrayList;

public class ChargeCalculatorActivity extends GDActivity {

    CarparkItem mCarpark;
    ArrayList<EditableItem> mItems;

    TextView mOutput;
    ListView mList;

    EditableCellAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.charge_calculator_activity);
        addActionBarItem(getGDActionBar()
                .newActionBarItem(NormalActionBarItem.class)
                .setDrawable(R.drawable.gd_action_bar_trashcan)
                .setContentDescription(R.string.gd_export), R.id.action_bar_reset);

        mOutput = (TextView) findViewById(R.id.tv_calculator_output);
        mList = (ListView) findViewById(R.id.lv_calculator_editibale);

        mCarpark = new CarparkItem();
        mCarpark.setmDevelopment(getIntent().getExtras().get("development").toString());
        mCarpark.setmBaseCharge(getIntent().getExtras().get("baseCharge").toString());

        prepareEditableItems();

        mAdapter = new EditableCellAdapter(this, mItems);
        mList.setAdapter(mAdapter);

        setTitle(mCarpark.getmDevelopment());
        mOutput.setText("$"+mCarpark.getmBaseCharge());
    }

    private void prepareEditableItems() {
        this.mItems = new ArrayList<EditableItem>();
        this.mItems.add(new EditableItem("Start Date", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("End Date", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Interval Type", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Interval Duration", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Extra Charges", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
    }

}
