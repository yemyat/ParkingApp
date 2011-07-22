package sg.srcode.xtremeapp.connection;

import android.content.Context;

public class NimbusServer {
    protected String SERVER_BASE_URL;
    protected final Context mContext;

    public interface Delegate {
        // Called if connection error occurred
        public void connectionError(String error);

        // Return empty error if success
        public void connectionEnded(String error, Object object);
    };

    public NimbusServer(Context context) {
        this.mContext = context;
        SERVER_BASE_URL = "https://api.projectnimbus.org/";
    }

    public void makeUrl() {

    }

    public String getSeverUrl() {
        return SERVER_BASE_URL;
    }


}
