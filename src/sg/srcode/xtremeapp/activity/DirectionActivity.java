package sg.srcode.xtremeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import greendroid.app.GDActivity;
import org.w3c.dom.Element;
import sg.srcode.xtremeapp.DashboardActivity;
import sg.srcode.xtremeapp.R;

public class DirectionActivity extends GDActivity {
    private TextView sourceLbl;
    private EditText sourceTxt;
    private Button confirmBtn;
    private Button currLocBtn;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setGDActionBarContentView(R.layout.direction_activity);

        sourceLbl = (TextView) findViewById(R.string.sourceLabel);
        sourceTxt = (EditText) findViewById(R.id.sourceText);
        confirmBtn = (Button) findViewById(R.string.confirmBtn);
        currLocBtn = (Button) findViewById(R.string.getCurrLocationBtn);

    }

    public void onConfirmSelection(View view) {
        if (sourceTxt.getText().toString().equalsIgnoreCase("")) {
            String errorMsg = "Please select your source address!";
            Toast errorNotification = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG);
            errorNotification.setGravity(Gravity.CENTER, errorNotification.getXOffset() / 2, errorNotification.getYOffset() / 2);
            errorNotification.show();
        }
        else {
            Intent intent = new Intent(DirectionActivity.this, DestinationAddActivity.class);
            DirectionActivity.this.startActivity(intent);
        }
    }

    public void onGetCurrLocation(View view) {
        if (sourceTxt.getText().toString().equalsIgnoreCase("")) {
            String errorMsg = "Please select your source address!";
            Toast errorNotification = Toast.makeText(this, errorMsg, Toast.LENGTH_LONG);
            errorNotification.setGravity(Gravity.CENTER, errorNotification.getXOffset() / 2, errorNotification.getYOffset() / 2);
            errorNotification.show();
        }
        else {
            Intent intent = new Intent(DirectionActivity.this, DestinationAddActivity.class);
            DirectionActivity.this.startActivity(intent);
        }
    }
}
