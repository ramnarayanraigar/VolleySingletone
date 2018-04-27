package raigar.ramnarayan.volleysingletone;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://www.500magic.com/a.php";
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        demoWebService();
    }

    /* Demo web service call for single ton call */
    public void demoWebService() {
        Map<String, String> param = new HashMap<>();
        JSONObject jsonObject = new JSONObject(param);
        NetworkManager.getInstance().somePostRequestReturningString(mContext, jsonObject, url, TAG, new SomeCustomListener<String>() {
            @Override
            public void getResult(String result, String errorDes, boolean isError) {
                if (isError) {
                    Log.v(TAG, " Error description: " +result);
                    Toast.makeText(mContext, errorDes, Toast.LENGTH_LONG).show();
                } else {
                  if (result.equals("null")) {
                      Log.v(TAG, "Response: " + result);
                  } else {
                      Log.v(TAG, "Response: " + result);
                      // parse json values
                  }
                }
            }
        });
    }
}
