package sg.srcode.xtremeapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import greendroid.app.GDActivity;
import greendroid.util.Time;
import greendroid.widget.GDActionBarItem;
import greendroid.widget.NormalActionBarItem;
import org.apache.http.entity.StringEntity;
import sg.srcode.xtremeapp.R;
import sg.srcode.xtremeapp.adapter.EditableCellAdapter;
import sg.srcode.xtremeapp.adapter.SectionedAdapter;
import sg.srcode.xtremeapp.item.CarparkItem;
import sg.srcode.xtremeapp.item.EditableItem;

import java.util.ArrayList;
import java.util.Calendar;

public class ChargeCalculatorActivity extends GDActivity implements AdapterView.OnItemClickListener, CarparkItem.CarparkDelegate {

    CarparkItem mCarpark;
    ArrayList<EditableItem> mItems;

    TextView mOutput;
    ListView mList;

    SectionedAdapter mAdapter;

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
        mCarpark.setmDelegate(this);

        mAdapter = new SectionedAdapter() {
            protected View getHeaderView(String caption, int index, int count, View convertView, ViewGroup parent) {
                TextView result = (TextView) convertView;

                if (convertView == null) {
                    result = (TextView) getLayoutInflater().inflate(R.layout.list_section_header, null);
                }
                result.setText(caption);

                return (result);
            }
        };

        prepareEditableItems();

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);

        setTitle(mCarpark.getmDevelopment());
        mOutput.setText("$" + mCarpark.getmBaseCharge());
    }


    private void prepareEditableItems() {
        this.mAdapter.removeAllSections();

        this.mItems = new ArrayList<EditableItem>();
        this.mItems.add(new EditableItem("Start Date", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Start Time", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mAdapter.addSection("Start date and time", new EditableCellAdapter(getBaseContext(), mItems));

        this.mItems = new ArrayList<EditableItem>();
        this.mItems.add(new EditableItem("End Date", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("End Time", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mAdapter.addSection("End date and time", new EditableCellAdapter(getBaseContext(), mItems));

        this.mItems = new ArrayList<EditableItem>();
        this.mItems.add(new EditableItem("Interval Duration", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Interval Charge", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mItems.add(new EditableItem("Optional Charges", "Click here to specify", getResources().getDrawable(R.drawable.icon)));
        this.mAdapter.addSection("Extras", new EditableCellAdapter(getBaseContext(), mItems));
    }

    @Override
    public boolean onHandleActionBarItemClick(GDActionBarItem item, int position) {
        switch (item.getItemId()) {
            case R.id.action_bar_reset:
                mCarpark.reset();
                prepareEditableItems();
                mAdapter.notifyDataSetChanged();
                break;
            default:
                return super.onHandleActionBarItemClick(item, position);
        }
        return true;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Calendar cal = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditableItem editableItem = (EditableItem) mAdapter.getItem(i);

        switch (i) {
            case 1:
                DatePickerDialog startDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editableItem.setmValue(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mCarpark.setmStartDate(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mAdapter.notifyDataSetChanged();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                startDateDialog.setTitle("Start Date");
                startDateDialog.show();
                break;
            case 2:
                TimePickerDialog startTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        editableItem.setmValue(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        mCarpark.setmStartTime(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        mAdapter.notifyDataSetChanged();
                    }
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                startTimeDialog.setTitle("Start Time");
                startTimeDialog.show();
                break;
            case 4:
                DatePickerDialog endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editableItem.setmValue(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mCarpark.setmEndDate(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                        mAdapter.notifyDataSetChanged();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                endDateDialog.setTitle("End Date");
                endDateDialog.show();
                break;
            case 5:
                TimePickerDialog endTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        editableItem.setmValue(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        mCarpark.setmEndTime(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                        mAdapter.notifyDataSetChanged();
                    }
                }, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true);
                endTimeDialog.setTitle("End Time");
                endTimeDialog.show();
                break;
            case 7:
                final CharSequence[] items = {"15 minutes", "30 minutes", "45 minutes", "60 minutes", "120 minutes", "180 minutes"};

                builder.setTitle("Set an Interval");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        editableItem.setmValue(items[item].toString());
                        mCarpark.setmIntervalDuration(items[item].toString());
                        mAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog intervalChooser = builder.create();
                intervalChooser.show();
                break;
            case 8:
                builder.setTitle("Set Rate");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        editableItem.setmValue(input.getText().toString());
                        mCarpark.setmIntervalRate(input.getText().toString());
                        mAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                builder.show();
                break;
            case 9:
                final CharSequence[] choices = {"Peak Hour Rate", "Weekend Rate"};
                builder.setTitle("Apply Extra Charges");
                boolean[] checked = new boolean[2];
                checked[0] = this.mCarpark.isPeakHour();
                checked[1] = this.mCarpark.isWeekend();
                builder.setMultiChoiceItems(choices, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (i == 0) {
                            mCarpark.setPeakHour(b);
                        }
                        if (i == 1) {
                            mCarpark.setWeekend(b);
                        }
                        editableItem.setmValue(updateTextView());
                        mAdapter.notifyDataSetChanged();
                    }
                });
                AlertDialog optionalChooser = builder.create();
                optionalChooser.show();

                break;
            default:
                break;
        }
    }

    private String updateTextView() {
        if (mCarpark.isPeakHour() && mCarpark.isWeekend()) {
            return "Peak Hour Rate/Weekend Rate";
        }
        if (mCarpark.isPeakHour()) {
            return "Peak Hour Rate";
        }
        if (mCarpark.isWeekend()) {
            return "Weekend Rate";
        }
        return "Click here to specify";
    }

    public void updatedValue(String charge) {
        mOutput.setText("$" + charge);
    }
}
