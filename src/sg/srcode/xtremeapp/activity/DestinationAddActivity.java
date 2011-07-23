package sg.srcode.xtremeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import greendroid.app.GDActivity;
import sg.srcode.xtremeapp.R;

public class DestinationAddActivity extends GDActivity {
    private EditText destinationTxt;
    private Button getResultButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.destination_activity);

        destinationTxt = (EditText) findViewById(R.id.destText);
        getResultButton = (Button) findViewById(R.string.getResultBtn);

    }

    public void onGetResult(View view) {
        if (destinationTxt.getText().toString().equalsIgnoreCase("")) {
            String errorMsg = "Please select your destination address!";
            Toast errorNotification = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG);
            errorNotification.setGravity(Gravity.CENTER, errorNotification.getXOffset() / 2, errorNotification.getYOffset() / 2);
            errorNotification.show();
        }
        else {
            String sourceAdd = getIntent().getStringExtra("sourceAdd");
            String lat = "";
            String lng = "";
            if (sourceAdd.equalsIgnoreCase("")) {
                lat = getIntent().getStringExtra("latitude");
                lng = getIntent().getStringExtra("longitude");
            }
           Intent intent = new Intent(DestinationAddActivity.this, ChargeCalculatorActivity.class);// to be amended
            if (sourceAdd.equalsIgnoreCase("")) {
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lng);
            }
            else {
                intent.putExtra("sourceAdd", sourceAdd);
            }
            intent.putExtra("destAdd", destinationTxt.getText().toString());
            DestinationAddActivity.this.startActivity(intent);
        }
    }
}
